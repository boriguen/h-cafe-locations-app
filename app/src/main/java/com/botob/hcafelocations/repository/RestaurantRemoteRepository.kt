package com.botob.hcafelocations.repository

import com.botob.hcafelocations.api.ChowNowApi
import com.botob.hcafelocations.api.models.Restaurant

class RestaurantRemoteRepository : RestaurantRepository {
    private val api = ChowNowApi.newInstance()

    override suspend fun fetchRestaurant(id: Int): Restaurant {
        return api.fetchRestaurant(id)
    }
}