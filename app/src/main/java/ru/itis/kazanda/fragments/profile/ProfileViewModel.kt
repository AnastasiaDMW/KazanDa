package ru.itis.kazanda.fragments.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.itis.kazanda.data.Favorite
import ru.itis.kazanda.database.PlaceDatabase

class ProfileViewModel (private val database: PlaceDatabase): ViewModel() {

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
    }

    fun delete(favorite: Favorite) {
        viewModelScope.launch {
            try {
                database.favoriteDao().delete(favorite)
                _favoriteList.value = database.favoriteDao().getUserFavorites().first()
            } catch (e: Exception) {
                Log.d("DATA", e.message.toString())
            }
        }
    }
}