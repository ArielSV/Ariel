package com.example.ariel.flows.moviesflow.actions

import com.example.ariel.flows.moviesflow.model.MovieDetailResponse

sealed class MoviesAction {
    data class GetMovies(val movies: List<MovieDetailResponse>) : MoviesAction()
    object GetDataFromDB : MoviesAction()
}