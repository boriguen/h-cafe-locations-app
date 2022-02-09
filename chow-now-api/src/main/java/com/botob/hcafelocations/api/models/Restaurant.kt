package com.botob.hcafelocations.api.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Restaurant(
    @SerializedName("id")
    var id: String,
    @SerializedName("address")
    var address: Address,
    @SerializedName("locations")
    var locations: List<Location>,
    @SerializedName("name")
    var name: String
) : Serializable
