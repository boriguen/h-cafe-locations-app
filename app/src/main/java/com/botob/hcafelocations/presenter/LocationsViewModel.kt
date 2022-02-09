package com.botob.hcafelocations.presenter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.botob.hcafelocations.api.models.Location
import com.botob.hcafelocations.repository.RestaurantRemoteRepository

class LocationsViewModel() : ViewModel() {
    val repository = RestaurantRemoteRepository()

    val locations: MutableLiveData<List<Location>> by lazy {
        MutableLiveData<List<Location>>()
    }

    suspend fun updateLocations(restaurantId: Int) {
        locations.value = repository.fetchRestaurant(restaurantId).locations
    }
}