package com.botob.hcafelocations.persistence

import androidx.room.*
import com.botob.hcafelocations.persistence.entities.Restaurant
import com.botob.hcafelocations.persistence.entities.RestaurantWithLocations

@Dao
interface RestaurantDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun putRestaurant(restaurant: Restaurant)

    @Query("SELECT * FROM Restaurant WHERE restaurantId = :id")
    fun getRestaurant(id: String): Restaurant

    @Transaction
    @Query("SELECT * FROM Restaurant WHERE restaurantId = :id")
    fun getRestaurantWithLocations(id: String): List<RestaurantWithLocations>


}