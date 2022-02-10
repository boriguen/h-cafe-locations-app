package com.botob.hcafelocations.persistence.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Address(
    @PrimaryKey val addressId: Int,
    val latitude: Double,
    val longitude: Double,
    val addressName: String?,
    val formattedAddress: String
)
