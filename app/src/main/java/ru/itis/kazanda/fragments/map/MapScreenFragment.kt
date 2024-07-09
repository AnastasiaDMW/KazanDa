package ru.itis.kazanda.fragments.map

import android.graphics.Outline
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.runtime.image.ImageProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.itis.kazanda.Constant
import ru.itis.kazanda.Constant.CATEGORY_TYPE
import ru.itis.kazanda.Constant.PRICE
import ru.itis.kazanda.R
import ru.itis.kazanda.data.Place
import ru.itis.kazanda.data.StateResult
import ru.itis.kazanda.databinding.FragmentMapScreenBinding

class MapScreenFragment : Fragment(R.layout.fragment_map_screen) {

    private var binding: FragmentMapScreenBinding? = null
    private var mapViewModel: MapViewModel? = null
    private var progressDialog: CustomProgressDialog? = null
    private var isExistCategory: Boolean = false

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMapScreenBinding.bind(view)

        progressDialog = CustomProgressDialog(requireContext())
        mapViewModel = MapViewModel()
        val price = arguments?.getInt(PRICE) ?: 0
        val categoryType = arguments?.getString(CATEGORY_TYPE) ?: "ERROR"

        Constant.categoryList.forEachIndexed { index, category ->
            if (category.title == categoryType){
                mapViewModel?.setCategoryId(index)
                isExistCategory = true
            }
        }
        if (!isExistCategory) {
            mapViewModel?.setCategoryId(Constant.categoryList.size)
        }
        binding?.run {
            progressDialog?.start("Поиск мест...")

            mvPlace.outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View, outline: Outline) {
                    val padding = 16
                    outline.setRoundRect(padding, padding, view.width - padding, view.height - padding, 50f)
                }
            }
            viewLifecycleOwner.lifecycleScope.launch {
                mapViewModel?.stateResult?.collect { result ->
                    when (result) {
                        is StateResult.Success -> {}
                        is StateResult.Info -> Snackbar.make(binding?.root!!, result.message, Snackbar.LENGTH_LONG).show()
                        is StateResult.Error -> Snackbar.make(binding?.root!!, result.message, Snackbar.LENGTH_LONG).show()
                        StateResult.Loading -> {
                            delay(2500L)
                            progressDialog?.stop()
                            BottomSheetBehavior.from(sheet).apply {
                                peekHeight = 60
                                this.state = BottomSheetBehavior.STATE_COLLAPSED
                                isHideable = false
                                isDraggable = false
                            }
                            secondConstraintL.visibility = ConstraintLayout.VISIBLE
                            val (userLat, userLon) = mapViewModel?.getUserLocation()!!
                            if (userLat != null && userLon != null) {
                                mvPlace?.apply {
                                    map.move(CameraPosition(Point(userLat, userLon), 16f, 0f, 0f))
                                }
                                val filteredList = mapViewModel?.getFilteredList()
                                if (filteredList != null) {
                                    displayPlacemarksOnMap(filteredList)
                                } else {
                                    Log.d("DisplayPlacemarksOnMap", "filteredList is null")
                                }
                            }
                        }
                        null -> Log.d("NULL", "Вернуло null")
                    }
                }
            }
            mapViewModel?.getData(root.context,0.5, price)
        }
    }

    private fun displayPlacemarksOnMap(filteredList: List<Place>) {

        binding?.run {

            val pinsCollection = mvPlace.map.mapObjects.addCollection()
            val points = filteredList.map { Point(it.latitude, it.longitude) }

            val imageProvider = ImageProvider.fromResource(root.context, R.drawable.pin)

            mvPlace.map.addCameraListener { p0, p1, p2, p3 ->
                points.forEach { point ->
                    pinsCollection.addPlacemark().apply {
                        geometry = point
                        setIcon(imageProvider)
                    }
                }
            }

            var placemarkTapListener = MapObjectTapListener { _, point ->
                val place: Place? = filteredList.find {
                    mapViewModel?.checkLocationMatch(
                        it.latitude.toString(),
                        it.longitude.toString(),
                        point.latitude.toString(),
                        point.longitude.toString()
                    )!!
                }
                if (place != null) {
                    BottomSheetBehavior.from(sheet).apply {
                        this.state = BottomSheetBehavior.STATE_COLLAPSED
                        this.state = BottomSheetBehavior.STATE_EXPANDED
                        isDraggable = true
                    }
                    clBottomSheetContainer.visibility = ConstraintLayout.VISIBLE
                    tvTitile.text = place.name
                    btnDescription.setOnClickListener {
                        val bundle = Bundle().apply {
                            putInt("placeId", place.id)
                        }
                        findNavController().navigate(
                            resId = R.id.action_mapScreenFragment_to_detailScreenFragment,
                            bundle
                        )
                    }
                    btnRoute.setOnClickListener {
                        //Мб сегодня сделаю, если не получится, уберу
                    }
                }
                true
            }
            pinsCollection.addTapListener(placemarkTapListener)
        }
    }

    companion object {
        fun bundle(categoryType: String, price: Int): Bundle = Bundle().apply {
            putString(CATEGORY_TYPE, categoryType)
            putInt(PRICE, price)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.mvPlace?.map?.mapObjects?.clear()
        binding = null
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding?.mvPlace?.onStart()
    }

    override fun onStop() {
        binding?.mvPlace?.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }
}