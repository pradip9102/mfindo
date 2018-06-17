package com.vizurd.travel.mfindo.networkRepo

import com.vizurd.travel.mfindo.models.Example
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query


interface NetworkRepository {
    @GET("maps/api/directions/json?key=AIzaSyCvlCU2hXhR1PjmXxbiu9fBzGpLfN_UkEY")
    fun listRepos(@Query("origin") source: String,
                  @Query("destination") destination: String,
                  @Query("mode") mode: String): Flowable<Example>
}