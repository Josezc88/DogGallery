package com.example.doggallery.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DogEntity::class], version = 1)
abstract class DogsDatabase: RoomDatabase() {
    abstract fun dogsDAO(): DogDAO


    companion object {
        @Volatile
        private var INSTANCE: DogsDatabase? = null

        fun getDatabase(context: Context): DogsDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    DogsDatabase::class.java,
                    "dogs.db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}