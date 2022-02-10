package com.botob.hcafelocations.presenter

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.botob.hcafelocations.api.models.Location
import com.botob.hcafelocations.persistence.RestaurantDatabase
import com.botob.hcafelocations.repository.RestaurantLocalRepository
import com.botob.hcafelocations.repository.RestaurantRemoteRepository

class LocationsViewModel(application: Application) : AndroidViewModel(application) {
    /**
     * The local repository.
     */
    private val localRepository =
        RestaurantLocalRepository(RestaurantDatabase.getDatabase(application).restaurantDao()   )

    /**
     * The remote repository.
     */
    private val remoteRepository = RestaurantRemoteRepository()

    /**
     * The live data to observe to get updated locations.
     */
    val locations: MutableLiveData<List<Location>> by lazy {
        MutableLiveData<List<Location>>()
    }

    /**
     * Updates the [locations] for the restaurant of given ID.
     */
    suspend fun updateLocations(restaurantId: Int) {
        // TODO: check the local repository before getting from the remote one.
        locations.value = remoteRepository.get(restaurantId).locations
    }
}