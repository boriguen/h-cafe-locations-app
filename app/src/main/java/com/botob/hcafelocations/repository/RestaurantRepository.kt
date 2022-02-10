package com.botob.hcafelocations.repository

import com.botob.hcafelocations.api.models.Restaurant

/**
 * [RestaurantRepository] is the interface describing the functions of a restaurant repository.
 */
interface RestaurantRepository {
    /**
     * Gets the restaurant of given ID.
     *
     * @return the matching [Restaurant].
     */
    suspend fun get(id: Int): Restaurant?

    /**
     * Puts a [Restaurant] in the store.
     */
    suspend fun put(restaurant: Restaurant) = Unit
}