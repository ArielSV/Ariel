package com.example.ariel.data.repository

import androidx.lifecycle.LiveData
import com.example.ariel.data.MoviesDao
import com.example.ariel.flows.moviesflow.model.MoviesRoomDB

class MoviesRepository(private val moviesDao: MoviesDao) {

    val readAllMovies : LiveData<List<MoviesRoomDB>> = moviesDao.getAllMovies()

    suspend fun  addMovies(movies: MoviesRoomDB) {
        moviesDao.addMovies(movies)
    }
}