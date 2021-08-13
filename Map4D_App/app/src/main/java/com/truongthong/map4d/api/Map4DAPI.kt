package com.truongthong.map4d.api

import com.truongthong.map4d.model.route.RouteLocation
import com.truongthong.map4d.model.search.MapResponse
import com.truongthong.map4d.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Map4DAPI {
    @GET("autosuggest")
    suspend fun searchLocation(
        @Query("text")
        searchQuery: String,
        @Query("key")
        key: String = API_KEY
    ): Response<MapResponse>

    @GET("route")
    suspend fun getRoute(
        @Query("key")
        key: String = API_KEY,
        @Query("origin")
        origin:String,
        @Query("destination")
        destination : String,
//        @Query("points")
//        points : String,
        @Query("mode")
        mode: String,
//        @Query("language")
//        language:String,
//        @Query("weighting")
//        weighting: Int
    ):Response<RouteLocation>
}