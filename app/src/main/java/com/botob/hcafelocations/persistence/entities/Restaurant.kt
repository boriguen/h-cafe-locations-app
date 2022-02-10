package com.botob.hcafelocations.persistence.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Restaurant(
    @PrimaryKey val restaurantId: String,
    @Embedded val address: Address,
    val locations: List<Location>,
    val restaurantName: String
)
