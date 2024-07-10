package ru.itis.kazanda.fragments.main

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import ru.itis.kazanda.R
import ru.itis.kazanda.databinding.ItemPlaceBinding

class PlaceViewHolder(
    private val binding: ItemPlaceBinding,
    private val glide: RequestManager,
    private val onClick: (Place) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private val requestOptions = RequestOptions
        .diskCacheStrategyOf(DiskCacheStrategy.ALL)

    fun onBind(place: Place) {
        binding.run {
            placeName.text = place.name
            binding.placePayment.text = PaymentType.entries[place.payment].toString()
            glide
                .load(place.imageUrls.split("\n")[0])
                .error(R.drawable.baseline_photo_camera_back_24)
                .placeholder(R.drawable.ic_launcher_foreground)
                .apply(requestOptions)
                .into(placeImage)
            val isFavorite = readFavoriteState(place.id)
            setFavoriteIcon(isFavorite)

            favoriteIcon.setOnClickListener {
                val newFavoriteState = !place.isFavorite
                place.isFavorite = newFavoriteState
                setFavoriteIcon(newFavoriteState)
                saveFavoriteState(place.id, newFavoriteState)
                true
            }
            root.setOnClickListener {
                onClick(place)
            }
        }
    }
    companion object {
        private const val FAVORITES_PREFS = "Favorites"
    }

    private fun setFavoriteIcon(isFavorite: Boolean) {
        binding.favoriteIcon.setImageResource(
            if (isFavorite) R.drawable.ic_favorite_heart_filled
            else R.drawable.ic_favorite_heart_empty
        )
    }

    private fun saveFavoriteState(placeId: Int, isFavorite: Boolean) {
        val sharedPreferences = itemView.context.getSharedPreferences(FAVORITES_PREFS, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean(placeId.toString(), isFavorite)
            apply()
        }
    }

    private fun readFavoriteState(placeId: Int): Boolean {
        val sharedPreferences = itemView.context.getSharedPreferences(FAVORITES_PREFS, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(placeId.toString(), false)
    }

}