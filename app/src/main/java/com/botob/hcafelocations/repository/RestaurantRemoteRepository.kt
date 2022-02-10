package com.botob.hcafelocations.repository

import com.botob.hcafelocations.api.ChowNowApi
import com.botob.hcafelocations.api.models.Restaurant

/**
 * [RestaurantRemoteRepository] is the implementation of [RestaurantRepository] fetching
 * restaurants from the API.
 */
class RestaurantRemoteRepository(private val api: ChowNowApi = ChowNowApi.newInstance()) : RestaurantRepository {
    /**
     *
     */
    override suspend fun get(id: Int): Restaurant {
        return api.fetchRestaurant(id)
    }
}