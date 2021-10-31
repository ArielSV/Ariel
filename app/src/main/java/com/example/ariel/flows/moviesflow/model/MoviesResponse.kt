package com.example.ariel.flows.moviesflow.model

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("page") val page: String?,
    @SerializedName("results") val results: List<MovieDetailResponse>?,
    @SerializedName("total_pages") val totalPages: Int?,
    @SerializedName("total_results") val totalResults: Int?
)