package ru.itis.kazanda.fragments.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import ru.itis.kazanda.Constant
import ru.itis.kazanda.data.Place
import ru.itis.kazanda.data.StateResult
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class MapViewModel: ViewModel() {

    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var filteredList: List<Place>? = null

    private var userLatitude: Double? = null
    private var userLongitude: Double? = null
    private var categoryId: Int? = null

    private val _stateResult = MutableStateFlow<StateResult>(StateResult.Loading)
    val stateResult: StateFlow<StateResult> = _stateResult.asStateFlow()

    private val placesList = listOf(
        Place(id = 0, name = "Скай парк", price = 700, latitude = 55.7997229, longitude = 49.1478210, categoryId = 0),
        Place(id = 1, name = "Pro coffe.shop", price = 1500, latitude = 55.8010952, longitude = 49.1411095, categoryId = 1),
        Place(id = 2, name = "Карл Фукс", price = 0, latitude = 55.7997517, longitude = 49.1299838, categoryId = 2),
        Place(id = 3, name = "Исфара", price = 500, latitude = 55.7971999, longitude = 49.1284360, categoryId = 0),
        Place(id = 4, name = "Cho", price = 550, latitude = 55.7962009, longitude = 49.1295261, categoryId = 1),
        Place(id = 5, name = "Дом-музей В.П.Аксенова", price = 1000, latitude = 55.7954638, longitude = 49.1351423, categoryId = 2),
    )

    fun setCategoryId(value: Int) {
        categoryId = value
    }

    fun getUserLocation(): Pair<Double?, Double?> {
        return Pair(userLatitude, userLongitude)
    }

    fun getFilteredList(): List<Place>? = filteredList

    fun getData(context: Context, radius: Double, price: Int) {
        viewModelScope.launch {
            val (userLat, userLng) = getLocation(context)
            _stateResult.value = findPlacesNearby(userLat, userLng, radius, price)
        }
    }

    private suspend fun getLocation(context: Context): Pair<Double, Double> {
        return suspendCancellableCoroutine { continuation ->
            try {
                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    continuation.resume(Pair(0.0, 0.0))
                    return@suspendCancellableCoroutine
                }
                fusedLocationProviderClient?.lastLocation?.addOnSuccessListener {
                    if (it != null) {
                        userLatitude = it.latitude
                        userLongitude = it.longitude
                        continuation.resume(Pair(it.latitude, it.longitude))
                    } else {
                        continuation.resume(Pair(0.0, 0.0))
                    }
                }?.addOnFailureListener { error ->
                    continuation.resumeWithException(error)
                }
            } catch (e: Exception) {
                continuation.resume(Pair(0.0, 0.0))
            }
        }
    }

    private fun findPlacesNearby(
        userLat: Double,
        userLng: Double,
        radius: Double = 2.0,
        price: Int
    ): StateResult {
        return try {
            val newList = placesList.filter { place ->
                val (lat, lng) = place.latitude to place.longitude
                val distance = calculateDistance(userLat to userLng, lat to lng, radius)
                distance <= radius && place.price <= price
            }.filter { place ->
                if (categoryId != Constant.categoryList.size) place.categoryId == categoryId
                else true
            }
            filteredList = newList
            StateResult.Loading
        } catch (e: NullPointerException) {
            StateResult.Error(e.message.toString())
        } catch (e: Exception) {
            StateResult.Error(e.message.toString())
        }
    }

    fun checkLocationMatch(
        latitude1: String,
        longitude1: String,
        latitude2: String,
        longitude2: String
    ): Boolean = latitude1.substring(0, 6) == latitude2.substring(0, 6) &&
            longitude1.substring(0, 6) == longitude2.substring(0, 6)


    private fun calculateDistance(
        userLocation: Pair<Double, Double>,
        placeLocation: Pair<Double, Double>,
        userRadius: Double
    ): Double {
        return if (userLatitude != null && userLongitude != null) {
            val (lat, lng) = placeLocation
            val (userLat, userLng) = userLocation
            val dLat = Math.toRadians(lat - userLat)
            val dLng = Math.toRadians(lng - userLng)
            val earthRadius = 6371

            val timeDist = sin(dLat/2) * sin(dLat/2) + cos(Math.toRadians(userLat)) * cos(Math.toRadians(lat)) * sin(dLng/2) * sin(dLng/2)
            val distance = 2* atan2(sqrt(timeDist), sqrt(1-timeDist))
            earthRadius * distance
        } else {
            userRadius + 1
        }
    }
}