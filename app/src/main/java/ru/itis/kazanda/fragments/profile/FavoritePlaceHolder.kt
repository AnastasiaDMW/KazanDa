package ru.itis.kazanda.fragments.profile

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import ru.itis.kazanda.R
import ru.itis.kazanda.data.Favorite
import ru.itis.kazanda.databinding.ItemFavoritePlaceBinding
import ru.itis.kazanda.fragments.main.PaymentType
import ru.itis.kazanda.fragments.main.Place

class FavoritePlaceHolder(
    private val binding: ItemFavoritePlaceBinding,
    private val glide: RequestManager,
    private val profileViewModel: ProfileViewModel,
    private val onClick: (Favorite) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private val requestOptions = RequestOptions
        .diskCacheStrategyOf(DiskCacheStrategy.ALL)

    fun onBind(place: Favorite) {
        binding.apply {
            placeName.text = place.title
            placePayment.text = PaymentType.entries[place.cost].toString()
            //получаем картинку из сети
            glide
                .load(place.imageUrls.split("\n")[0])
                .error(R.drawable.baseline_photo_camera_back_24)
                .placeholder(R.drawable.ic_launcher_foreground)
                .apply(requestOptions)
                .into(placeImage)
            //val isFavorite = readFavoriteState(place.id)
            setFavoriteIcon(true)

            favoriteIcon.setOnClickListener {
                place.let {
                    profileViewModel.delete(it)
                }
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

    /*private fun saveFavoriteState(placeId: Int, isFavorite: Boolean) {
        val sharedPreferences = itemView.context.getSharedPreferences("Favorites", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean(placeId.toString(), isFavorite)
            apply()
        }
    }

    private fun readFavoriteState(placeId: Int): Boolean {
        val sharedPreferences = itemView.context.getSharedPreferences("Favorites", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(placeId.toString(), false)
    }*/
}

/*
favoriteIcon.setOnClickListener {

    val newFavoriteState = !place.isFavorite
    place.isFavorite = newFavoriteState
    setFavoriteIcon(newFavoriteState)
    saveFavoriteState(place.id, newFavoriteState)
    true
}*/
