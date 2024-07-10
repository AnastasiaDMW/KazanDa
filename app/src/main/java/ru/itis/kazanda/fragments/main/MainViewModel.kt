package ru.itis.kazanda.fragments.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.itis.kazanda.data.Favorite
import ru.itis.kazanda.data.Place
import ru.itis.kazanda.database.PlaceDatabase

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val db: PlaceDatabase = PlaceDatabase.getDatabase(application)

    val places = db.placeDao().getAllPlaces().asLiveData()

    fun getFilteredPlaces(query: String): LiveData<List<Place>> {
        return db.placeDao().getFilteredPlaces("%$query%").asLiveData()
    }

    fun getFilteredByPayment(minCost: Int, maxCost: Int): LiveData<List<Place>> {
        return db.placeDao().getFilteredByPayment(minCost, maxCost).asLiveData()
    }

    fun isPlaceFavorite(placeId: Int): LiveData<Boolean> {
        return db.favoriteDao().isFavorite(placeId).asLiveData().map { it != null }
    }

    fun toggleFavorite(place: Place) {
        viewModelScope.launch {
            val isFavorite = db.favoriteDao().isFavoriteSynchronously(place.id) != null
            if (isFavorite) {
                removePlaceFromFavorites(place)
            } else {
                addPlaceToFavorites(place)
            }
        }
    }

    fun addPlaceToFavorites(place: Place) {
        viewModelScope.launch {
            db.favoriteDao().insert(Favorite(place.id, place.title, place.cost, place.categoryId, place.latitude, place.longitude, place.hours, place.description, place.imageUrls, place.address))
        }
    }

    fun removePlaceFromFavorites(place: Place) {
        viewModelScope.launch {
            db.favoriteDao().delete(Favorite(place.id, place.title, place.cost, place.categoryId, place.latitude, place.longitude, place.hours, place.description, place.imageUrls, place.address))
        }
    }

}
