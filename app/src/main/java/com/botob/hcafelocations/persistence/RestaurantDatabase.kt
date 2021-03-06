package com.botob.hcafelocations.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.botob.hcafelocations.persistence.entities.Address
import com.botob.hcafelocations.persistence.entities.Location
import com.botob.hcafelocations.persistence.entities.Restaurant

@Database(
    entities = [Restaurant::class, Address::class, Location::class],
    version = 2
)
@TypeConverters(Converters::class)
abstract class RestaurantDatabase : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao

    companion object {
        @Volatile
        private var INSTANCE: RestaurantDatabase? = null

        fun getDatabase(context: Context): RestaurantDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RestaurantDatabase::class.java,
                    "restaurant_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}