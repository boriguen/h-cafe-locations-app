package com.botob.hcafelocations.repository

import com.botob.hcafelocations.api.models.Address
import com.botob.hcafelocations.api.models.Location
import com.botob.hcafelocations.api.models.Restaurant
import com.botob.hcafelocations.persistence.RestaurantDao
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
    override suspend fun get(id: Int): Restaurant {
        val result = restaurantDao.getRestaurantWithLocations(id.toString())
        val localRestaurant = result.first().restaurant
        val localLocations = result.first().locations

        val address = localRestaurant.address.let {
            Address(it.addressId, it.latitude, it.longitude, it.addressName, it.formattedAddress)
        }

        val locations = localLocations.map { location ->
            val locationAddress = location.address.let {
                Address(it.addressId, it.latitude, it.longitude, it.addressName, it.formattedAddress)
            }
            Location(location.locationId, locationAddress, location.locationName)
        }

        return Restaurant(
            localRestaurant.restaurantId,
            address,
            locations,
            localRestaurant.restaurantName
        )
    }

    /**
     *
     */
    override suspend fun put(restaurant: Restaurant) {
        val localAddress = LocalAddress(
            restaurant.address.id,
            restaurant.address.latitude,
            restaurant.address.longitude,
            restaurant.address.name,
            restaurant.address.formattedAddress
        )

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
}