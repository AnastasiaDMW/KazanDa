package ru.itis.kazanda.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.itis.kazanda.dao.FavoriteDao
import ru.itis.kazanda.dao.PlaceDao
import ru.itis.kazanda.data.Favorite
import ru.itis.kazanda.data.Place

@Database(entities = [Place::class, Favorite::class], version = 1, exportSchema = false)
abstract class PlaceDatabase: RoomDatabase() {

    abstract fun placeDao(): PlaceDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: PlaceDatabase? = null

        fun getDatabase(context: Context): PlaceDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    PlaceDatabase::class.java, "placeDB.db"
                )
                    .createFromAsset("placeDB.db")
                    .build()
                    .also {
                        INSTANCE = it
                    }
            }
        }
    }

}