package com.botob.hcafelocations.api.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Location(
    @SerializedName("id")
    var id: String,
    @SerializedName("address")
    var address: Address,
    @SerializedName("name")
    var name: String?,
) : Serializable
