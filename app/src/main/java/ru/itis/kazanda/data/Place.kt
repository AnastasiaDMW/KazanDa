package ru.itis.kazanda.data
data class Place(
    val id: Int,
    val name: String,
    val price: Int,
    val categoryId: Int,
    val latitude: Double,
    val longitude: Double
)
