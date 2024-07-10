package ru.itis.kazanda.fragments.profile

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.itis.kazanda.data.Favorite
import ru.itis.kazanda.data.Place
import ru.itis.kazanda.database.PlaceDatabase

class ProfileViewModel (application: Application) : AndroidViewModel(application)  {
    private val db: PlaceDatabase = PlaceDatabase.getDatabase(application)

    val favoriteList = db.favoriteDao().getUserFavorites().asLiveData()

    /*private val database = PlaceDatabase.getDatabase(context)
    private val _favoriteList = MutableLiveData<List<Favorite>>()
    val favoriteList: LiveData<List<Favorite>> = _favoriteList

    fun getFavoritePlaces() {
        viewModelScope.launch {
            try {
                _favoriteList.value = database.favoriteDao().getUserFavorites().first()
            } catch (e: Exception) {
                Log.d("DATA", e.message.toString())
            }
        }
    }*/

    fun removePlaceFromFavorites(place: Favorite) {
        viewModelScope.launch {
            db.favoriteDao().delete(Favorite(place.id, place.title, place.cost, place.categoryId, place.latitude, place.longitude, place.hours, place.description, place.imageUrls, place.address))
        }
    }
}