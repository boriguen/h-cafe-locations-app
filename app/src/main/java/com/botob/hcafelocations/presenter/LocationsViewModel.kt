package com.botob.hcafelocations.presenter

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.botob.hcafelocations.api.models.Location
import com.botob.hcafelocations.persistence.RestaurantDatabase
import com.botob.hcafelocations.repository.RestaurantLocalRepository
import com.botob.hcafelocations.repository.RestaurantRemoteRepository

class LocationsViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        /**
         * The tag for logging.
         */
        private val TAG = LocationsViewModel::class.java.simpleName
    }

    /**
     * The local repository.
     */
    private val localRepository =
        RestaurantLocalRepository(RestaurantDatabase.getDatabase(application).restaurantDao())

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
        localRepository.get(restaurantId)?.let {
            locations.value = it.locations
            Log.d(TAG, "Getting the restaurant ${it.name} from the local repository")
        } ?: run {
            remoteRepository.get(restaurantId)?.let {
                localRepository.put(it)
                locations.value = it.locations
            }
        }
    }
}