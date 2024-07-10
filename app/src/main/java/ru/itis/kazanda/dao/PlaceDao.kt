package ru.itis.kazanda.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.itis.kazanda.data.Place

@Dao
interface PlaceDao {

    @Query("SELECT * FROM place")
    fun getAllPlaces(): Flow<List<Place>>

}