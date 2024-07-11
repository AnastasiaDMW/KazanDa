package ru.itis.kazanda.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.itis.kazanda.data.Place

@Dao
interface PlaceDao {

    @Query("SELECT * FROM place")
    fun getAllPlaces(): Flow<List<Place>>
    @Query("SELECT * FROM place WHERE id = :id")
    fun getPlaceById(id: Int): Flow<Place>
    @Query("SELECT * FROM place WHERE title LIKE :query")
    fun getFilteredPlaces(query: String): Flow<List<Place>>
    @Query("SELECT * FROM place WHERE cost BETWEEN :minCost AND :maxCost")
    fun getFilteredByPayment(minCost: Int, maxCost: Int): Flow<List<Place>>
    @Query("SELECT * FROM place WHERE categoryId = :categoryId")
    fun getFilteredByCategory(categoryId: Int): Flow<List<Place>>
}