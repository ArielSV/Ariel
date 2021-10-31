package com.example.ariel.flows.moviesflow.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieDetailResponse(
    @SerializedName("adult") val adult: Boolean?,
    @SerializedName("id") val id: Int?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("original_title") val originalTitle: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("popularity") val popularity: Double?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("vote_count") val voteCount: Int?
) : Parcelable