package com.example.ariel.data.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.ariel.data.MoviesDataBase
import com.example.ariel.data.repository.MoviesRepository
import com.example.ariel.flows.moviesflow.model.MoviesRoomDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoviesRoomViewModel(application: Application) : AndroidViewModel(application) {

    val readAllMovies: LiveData<List<MoviesRoomDB>>
    private val repository: MoviesRepository

    init {
        val moviesDao = MoviesDataBase.getDatabase(application).moviesDao()
        repository = MoviesRepository(moviesDao)
        readAllMovies = repository.readAllMovies
    }

    fun addMovie(movie: MoviesRoomDB) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMovies(movie)
        }
    }

}