package com.example.ariel.flows.moviesflow.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieDetailRoomDB(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val backdropPath: String?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val voteAverage: Double?,
    val voteCount: Int?
)