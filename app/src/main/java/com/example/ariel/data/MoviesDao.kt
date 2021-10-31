package com.example.ariel.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.ariel.flows.moviesflow.model.MoviesRoomDB

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMovies(movies: MoviesRoomDB)

    @Query("SELECT * FROM movies ORDER BY id ASC")
    fun getAllMovies(): LiveData<List<MoviesRoomDB>>
}