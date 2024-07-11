package ru.itis.kazanda.fragments.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.itis.kazanda.data.Favorite
import ru.itis.kazanda.database.PlaceDatabase

class ProfileViewModel (application: Application) : AndroidViewModel(application)  {
    private val db: PlaceDatabase = PlaceDatabase.getDatabase(application)

    val favoriteList = db.favoriteDao().getUserFavorites().asLiveData()

    fun removePlaceFromFavorites(place: Favorite) {
        viewModelScope.launch {
            db.favoriteDao().delete(Favorite(place.id, place.title, place.cost, place.categoryId, place.latitude, place.longitude, place.hours, place.description, place.imageUrls, place.address))
        }
    }
}