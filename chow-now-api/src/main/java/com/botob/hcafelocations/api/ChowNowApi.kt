package com.botob.hcafelocations.api

import com.botob.hcafelocations.api.models.Restaurant
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * [ChowNowApi] is the interface to use with Retrofit to be able to call the ChowNow backend.
 */
interface ChowNowApi {
    /**
     *
     */
    companion object {
        /**
         * Creates a new instance of [ChowNowApi] via Retrofit.
         */
        fun newInstance(): ChowNowApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.CHOW_NOW_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient.Builder().build())
                .build()
            return retrofit.create(ChowNowApi::class.java)
        }
    }

    /**
     * Fetches the restaurant of given ID.
     */
    @GET("company/{id}")
    suspend fun fetchRestaurant(@Path("id") restaurantId: Int): Restaurant
}