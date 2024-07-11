package ru.itis.kazanda.fragments.profile

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import ru.itis.kazanda.R
import ru.itis.kazanda.data.Favorite
import ru.itis.kazanda.databinding.ItemFavoritePlaceBinding

class FavoritePlaceHolder(
    private val binding: ItemFavoritePlaceBinding,
    private val glide: RequestManager,
    private val onClick: (Favorite) -> Unit,
    private val removePlaceFromFavorites: (Favorite) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private val requestOptions = RequestOptions
        .diskCacheStrategyOf(DiskCacheStrategy.ALL)

    fun onBind(place: Favorite) {
        binding.apply {
            placeName.text = place.title
            placePayment.text = place.cost.toString()
            //получаем картинку из сети
            glide
                .load(place.imageUrls.split("\\n")[0])
                .error(R.drawable.baseline_photo_camera_back_24)
                .placeholder(R.drawable.ic_launcher_foreground)
                .apply(requestOptions)
                .into(placeImage)
            setFavoriteIcon(true)

            favoriteIcon.setOnClickListener {
                removePlaceFromFavorites(place)
                setFavoriteIcon(false)
            }
            root.setOnClickListener {
                onClick.invoke(place)
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