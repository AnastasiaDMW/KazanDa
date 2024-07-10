package ru.itis.kazanda.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val cost: Int,
    val categoryId: Int,
    val latitude: Double,
    val longitude: Double,
    val hours: String,
    val description: String,
    val imageUrls: String,
    val address: String
)