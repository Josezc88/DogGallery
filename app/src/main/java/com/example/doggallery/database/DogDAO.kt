package com.example.doggallery.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.doggallery.retrofit.Dog
import com.example.doggallery.retrofit.Dogs


@Dao
interface DogDAO {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(dog: DogEntity)

    @Query("DELETE FROM Dogs WHERE image = :image")
    suspend fun delete(image: String): Int

    @Query("SELECT * FROM Dogs ORDER BY id DESC")
    suspend fun getFavorites(): List<DogEntity>
}