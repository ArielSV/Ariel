package com.example.ariel.flows.moviesflow.repositorie

import com.example.ariel.flows.moviesflow.model.MoviesResponse
import com.example.ariel.network.DemoApiClient
import com.example.ariel.utils.applySchedulers
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class DemoRepository @Inject constructor(
    private val apiService: DemoApiClient
) {

    private val apiKey = "0e98ff58f3c621d5ed67fcbfe06dcecc"

    fun getMovies(): Single<MoviesResponse> {
        return apiService.getMovies(apiKey).applySchedulers()
    }
}