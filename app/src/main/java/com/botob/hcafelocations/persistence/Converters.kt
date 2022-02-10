package com.botob.hcafelocations.persistence

import androidx.room.TypeConverter
import com.botob.hcafelocations.persistence.entities.Location
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {
    @TypeConverter
    fun fromString(value: String?): List<Location?>? {
        val listType: Type = object : TypeToken<List<Location?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Location?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}