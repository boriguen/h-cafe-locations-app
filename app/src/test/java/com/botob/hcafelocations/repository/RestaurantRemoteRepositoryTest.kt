package com.botob.hcafelocations.repository

import com.botob.hcafelocations.api.ChowNowApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class RestaurantRemoteRepositoryTest {
    companion object {
        private const val RESTAURANT_ID = 1

        private const val EXPECTED_RESTAURANT_NAME = "The H Cafe"
        private const val EXPECTED_FORMATTED_ADDRESS = "1700 Lincoln Blvd., Apt 6, Venice CA 90291"
        private const val EXPECTED_LOCATIONS_COUNT = 21
    }

    @Test
    fun testFetchRestaurant() = runBlocking {
        val restaurant = ChowNowApi.newInstance().fetchRestaurant(RESTAURANT_ID)

        Assert.assertEquals(EXPECTED_RESTAURANT_NAME, restaurant.name)
        Assert.assertEquals(EXPECTED_FORMATTED_ADDRESS, restaurant.address.formattedAddress)
        Assert.assertEquals(EXPECTED_LOCATIONS_COUNT, restaurant.locations.size)
    }
}