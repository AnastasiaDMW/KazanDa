package ru.itis.kazanda.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.itis.kazanda.data.Favorite

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorite")
    fun getUserFavorites(): Flow<List<Favorite>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(favorite: Favorite)

    @Delete
    suspend fun delete(favorite: Favorite)

    @Query("SELECT * FROM favorite WHERE id = :placeId LIMIT 1")
    fun isFavorite(placeId: Int): Flow<Favorite?>
    @Query("SELECT * FROM favorite WHERE id = :placeId LIMIT 1")
    suspend fun isFavoriteSynchronously(placeId: Int): Favorite?
}