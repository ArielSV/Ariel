package com.example.ariel.flows.moviesflow.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MoviesRoomDB(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val backdropPath: String?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    val releaseDate: String?,
    val voteAverage: Double?,
    val voteCount: Int?
)