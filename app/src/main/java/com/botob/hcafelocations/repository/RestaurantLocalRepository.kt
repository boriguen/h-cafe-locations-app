package com.botob.hcafelocations.repository

import com.botob.hcafelocations.api.models.Address
import com.botob.hcafelocations.api.models.Location
import com.botob.hcafelocations.api.models.Restaurant
import com.botob.hcafelocations.persistence.RestaurantDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import com.botob.hcafelocations.persistence.entities.Address as LocalAddress
import com.botob.hcafelocations.persistence.entities.Location as LocalLocation
import com.botob.hcafelocations.persistence.entities.Restaurant as LocalRestaurant

/**
 * [RestaurantLocalRepository] is the implementation of [RestaurantRepository] fetching
 * restaurants from the API.
 */
class RestaurantLocalRepository(private val restaurantDao: RestaurantDao) : RestaurantRepository {
    /**
     *
     */
    override suspend fun get(id: Int): Restaurant? {
        val deferred = CoroutineScope(Dispatchers.IO).async {
            restaurantDao.getRestaurantWithLocations(id.toString())
        }

        val result = deferred.await()
        if (result.isNotEmpty()) {
            val localRestaurant = result.first().restaurant

            val address = createAddress(localRestaurant.address)

            val locations = localRestaurant.locations.map { location ->
                val locationAddress = createAddress(location.address)
                Location(location.locationId, locationAddress, location.locationName)
            }

            return Restaurant(
                localRestaurant.restaurantId,
                address,
                locations,
                localRestaurant.restaurantName
            )
        } else {
            return null
        }
    }

    /**
     *
     */
    override suspend fun put(restaurant: Restaurant) {
        val localAddress = createLocalAddress(restaurant.address)

        val localLocations = restaurant.locations.map { location ->
            val locationAddress = location.address.let {
                LocalAddress(it.id, it.latitude, it.longitude, it.name, it.formattedAddress)
            }
            LocalLocation(location.id, locationAddress, location.name, restaurant.id)
        }

        val localRestaurant = LocalRestaurant(
            restaurant.id,
            localAddress,
            localLocations,
            restaurant.name
        )

        restaurantDao.putRestaurant(localRestaurant)
    }

    private fun createAddress(localAddress: LocalAddress): Address {
        return Address(
            localAddress.addressId,
            localAddress.latitude,
            localAddress.longitude,
            localAddress.addressName,
            localAddress.formattedAddress
        )
    }

    private fun createLocalAddress(address: Address): LocalAddress {
        return LocalAddress(
            address.id,
            address.latitude,
            address.longitude,
            address.name,
            address.formattedAddress
        )
    }
}