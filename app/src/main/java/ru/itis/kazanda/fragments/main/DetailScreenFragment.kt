package ru.itis.kazanda.fragments.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import ru.itis.kazanda.Constant
import ru.itis.kazanda.R
import ru.itis.kazanda.data.Place
import ru.itis.kazanda.databinding.FragmentDetailScreenBinding

class DetailScreenFragment : Fragment(R.layout.fragment_detail_screen) {
    private lateinit var binding: FragmentDetailScreenBinding
    private val viewModel: PlaceViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailScreenBinding.bind(view)
        val placeId = arguments?.getInt("placeId") ?: return
        viewModel.getPlaceById(placeId).observe(viewLifecycleOwner) { place ->
            updateUI(place)
        }

        binding.toggleDescriptionIcon.setOnClickListener {
            toggleDescription()
        }

        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
    private fun toggleDescription() {
        if (binding.placeDescription.visibility == View.GONE) {
            binding.placeDescription.visibility = View.VISIBLE
            binding.toggleDescriptionIcon.setImageResource(R.drawable.baseline_expand_less_24)
        } else {
            binding.placeDescription.visibility = View.GONE
            binding.toggleDescriptionIcon.setImageResource(R.drawable.baseline_expand_more_24)
        }
    }
    private fun updateUI(place: Place) {
        val categoryTitle = Constant.categoryList.find { it.id == place.categoryId }?.title ?: "Неизвестная категория"
        binding.apply {
            placeName.text = place.title
            placeCategory.text = categoryTitle
            placePrice.text = "${place.cost}₽"
            placeAddress.text = place.address
            placeHours.text = place.hours
            placeDescription.text = place.description
            setupImageCarousel(place)
        }
    }

    private fun setupImageCarousel(place: Place) {
        val images = place.imageUrls.split("\\n").filter { it.isNotBlank() }
        binding.photoViewPager.adapter = ImageSliderAdapter(images)
        binding.photoViewPager.offscreenPageLimit = 1
        val compositePageTransformer = CompositePageTransformer().apply {
            addTransformer(MarginPageTransformer(resources.getDimensionPixelSize(R.dimen.viewpager_margin)))
            addTransformer { page, position ->
                val r: Float = 1 - Math.abs(position)
                page.scaleY = 0.85f + r * 0.15f
                page.alpha = 0.25f + r * 0.75f
            }
        }
        binding.photoViewPager.setPageTransformer(compositePageTransformer)
        binding.photoViewPager.setCurrentItem(Integer.MAX_VALUE / 2, false)
    }

}