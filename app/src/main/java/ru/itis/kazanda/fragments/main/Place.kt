package ru.itis.kazanda.fragments.main

data class Place(
    val id: Int,
    val name: String,
    val payment: Int,
    val address: String,
    val hours: String,
    val description: String,
    val imageUrls: String,
)