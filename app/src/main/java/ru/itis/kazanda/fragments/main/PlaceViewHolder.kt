package ru.itis.kazanda.fragments.main

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
            placePayment.text = when(place.payment){
                0-> "free"
                1->"$"
                2->"$$"
                else -> "$$$"
            }
            glide
                .load(place.imageUrls.split("\n")[0])
                .error(R.drawable.baseline_photo_camera_back_24)
                .placeholder(R.drawable.ic_launcher_foreground)
                .apply(requestOptions)
                .into(placeImage)

            root.setOnClickListener {
                onClick(place)
            }
        }
    }
}