package ru.itis.kazanda.fragments.main

import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import ru.itis.kazanda.R
import ru.itis.kazanda.data.Place
import ru.itis.kazanda.databinding.ItemPlaceBinding

class PlaceViewHolder(
    val binding: ItemPlaceBinding,
    private val glide: RequestManager,
    private val onClick: (Place) -> Unit,
    private val toggleFavorite: (Place) -> Unit,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.ViewHolder(binding.root) {

    private val requestOptions = RequestOptions
        .diskCacheStrategyOf(DiskCacheStrategy.ALL)

    fun onBind(place: Place, isFavorite: Boolean) {
        binding.run {
            placeName.text = place.title
            placePayment.text = if (place.cost > 0) "${place.cost} ₽" else "Бесплатно"
            glide
                .load(place.imageUrls.split("\\n")[0])
                .error(R.drawable.baseline_photo_camera_back_24)
                .placeholder(R.drawable.ic_launcher_foreground)
                .apply(requestOptions)
                .into(placeImage)
            setFavoriteIcon(isFavorite)

            favoriteIcon.setOnClickListener {
                setFavoriteIcon(!isFavorite)
                toggleFavorite(place)
            }
            binding.root.setOnClickListener {
                onClick(place)
            }
        }
    }
    private fun setFavoriteIcon(isFavorite: Boolean) {
        binding.favoriteIcon.setImageResource(
            if (isFavorite) R.drawable.ic_favorite_heart_filled
            else R.drawable.ic_favorite_heart_empty
        )
    }

}