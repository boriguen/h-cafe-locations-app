package com.botob.hcafelocations.persistence.entities

import androidx.room.Embedded
import androidx.room.Relation

data class RestaurantWithLocations(
    @Embedded val restaurant: Restaurant,
    @Relation(
        parentColumn = "restaurantId",
        entityColumn = "restaurantId"
    ) val locations: List<Location>
)