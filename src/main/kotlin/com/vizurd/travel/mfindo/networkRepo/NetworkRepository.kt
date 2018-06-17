package com.vizurd.travel.mfindo.networkRepo

import com.vizurd.travel.mfindo.models.Example
import com.vizurd.travel.mfindo.utils.Constants
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query


interface NetworkRepository {
    @GET("maps/api/directions/json?key=${Constants.GOOGLE_MAP_API_KEY}")
    fun listRepos(
            @Query("origin") source: String,
            @Query("destination") destination: String,
            @Query("mode") mode: String
    ): Flowable<Example>
}