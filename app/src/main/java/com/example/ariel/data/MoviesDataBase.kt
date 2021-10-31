package com.example.ariel.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ariel.flows.moviesflow.model.MoviesRoomDB

@Database(entities = [MoviesRoomDB::class], version = 1, exportSchema = false)
abstract class MoviesDataBase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

    companion object {
        @Volatile
        private var INSTANCE: MoviesDataBase? = null

        fun getDatabase(context: Context): MoviesDataBase {
            val temInstance = INSTANCE
            if (temInstance != null) {
                return temInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    MoviesDataBase::class.java,
                    "movies_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}