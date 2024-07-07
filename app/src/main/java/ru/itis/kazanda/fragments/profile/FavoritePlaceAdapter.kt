package ru.itis.kazanda.fragments.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ru.itis.kazanda.databinding.ItemFavoritePlaceBinding
import ru.itis.kazanda.fragments.main.Place

class FavoritePlaceAdapter(
    private val list: List<Place>,
    private val glide: RequestManager,
    private val onClick: (Place) -> Unit,
) : RecyclerView.Adapter<FavoritePlaceHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoritePlaceHolder = FavoritePlaceHolder(
        binding = ItemFavoritePlaceBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        glide = glide,
        onClick = onClick,
    )

    override fun onBindViewHolder(holder: FavoritePlaceHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size
}