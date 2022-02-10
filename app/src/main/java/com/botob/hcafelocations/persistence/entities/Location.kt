package com.botob.hcafelocations.persistence.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Location(
    @PrimaryKey val locationId: String,
    @Embedded val address: Address,
    val locationName: String?,
    val restaurantId: String
)
