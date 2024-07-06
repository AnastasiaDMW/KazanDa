package ru.itis.kazanda.fragments.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.RequestManager
import ru.itis.kazanda.databinding.ItemPlaceBinding

class PlaceAdapter(
    private val glide: RequestManager,
    private val onClick: (Place) -> Unit,
) : ListAdapter<Place, PlaceViewHolder>(diffCallback) {

    init {
        setHasStableIds(true)
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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val binding = ItemPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaceViewHolder(binding, glide, onClick)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.toLong()
    }

    fun filterPlaces(query: String) {
        val fullList = PlaceRepository.places
        val filteredPlaces = if (query.isEmpty()) {
            fullList
        } else {
            fullList.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
        submitList(filteredPlaces)
    }
}
