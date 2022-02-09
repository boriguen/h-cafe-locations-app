package com.botob.hcafelocations.repository

import com.botob.hcafelocations.api.models.Restaurant

interface RestaurantRepository {
    suspend fun fetchRestaurant(id: Int): Restaurant
}