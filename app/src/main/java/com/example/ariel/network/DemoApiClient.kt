package com.example.ariel.network

import com.example.ariel.flows.moviesflow.model.MoviesResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface DemoApiClient {
    @GET("discover/movie?primary_release_date.gte=2014-09-15&primary_release_date.lte=2014-10-22")
    fun getMovies(@Query("api_key") apiKey: String): Single<MoviesResponse>
}