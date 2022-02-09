package com.botob.hcafelocations.api.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Address(
    @SerializedName("id")
    var id: Int,
    @SerializedName("latitude")
    var latitude: Double,
    @SerializedName("longitude")
    var longitude: Double,
    @SerializedName("name")
    var name: String,
    @SerializedName("formatted_address")
    var formattedAddress: String
) : Serializable
