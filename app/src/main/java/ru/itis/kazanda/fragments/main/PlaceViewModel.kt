package ru.itis.kazanda.fragments.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import ru.itis.kazanda.data.Place
import ru.itis.kazanda.database.PlaceDatabase

class PlaceViewModel(application: Application) : AndroidViewModel(application) {
    private val db: PlaceDatabase = PlaceDatabase.getDatabase(application)

    fun getPlaceById(id: Int): LiveData<Place> {
        return db.placeDao().getPlaceById(id).asLiveData()
    }
}