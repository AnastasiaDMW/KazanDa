package ru.itis.kazanda.fragments.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.RequestManager
import ru.itis.kazanda.databinding.ItemPlaceBinding
import ru.itis.kazanda.data.Place


class PlaceAdapter(
    private val glide: RequestManager,
    private val viewModel: MainViewModel,
    private val lifecycleOwner: LifecycleOwner,
    private val onClick: (Place) -> Unit,
) : ListAdapter<Place, PlaceViewHolder>(diffCallback) {
    private var originalList: List<Place> = listOf()
    private var filteredPlacesByCategory: List<Place> = listOf()
    private var filteredPlacesByPrice: List<Place> = listOf()
    init {
        setHasStableIds(true)
        viewModel.places.observe(lifecycleOwner) { places ->
            originalList = places
            submitList(places)
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Place>() {
            override fun areItemsTheSame(oldItem: Place, newItem: Place): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Place, newItem: Place): Boolean {
                return oldItem == newItem
            }
        }
        const val ALL_CATEGORIES_ID = -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val binding = ItemPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaceViewHolder(binding, glide, onClick, viewModel::toggleFavorite,lifecycleOwner)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val place = getItem(position)
        viewModel.isPlaceFavorite(place.id).observe(lifecycleOwner) { isFavorite ->
            holder.onBind(place, isFavorite)
        }
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }

    fun filterPlaces(query: String) {
        viewModel.getFilteredPlaces(query).observe(lifecycleOwner) { places ->
            originalList = places
            submitList(places)
        }
    }

    fun filterByCategory(categoryId: Int) {
        viewModel.getFilteredByCategory(categoryId).observe(lifecycleOwner) { places ->
            filteredPlacesByCategory = places
            combineFilters()
        }
    }

    fun filterByPriceRange(minCost: Int, maxCost: Int) {
        viewModel.getFilteredByPayment(minCost, maxCost).observe(lifecycleOwner) { places ->
            filteredPlacesByPrice = places
            combineFilters()
        }
    }

    private fun combineFilters() {
        val combinedList = filteredPlacesByCategory.intersect(filteredPlacesByPrice).toList()
        submitList(combinedList)
    }

}
