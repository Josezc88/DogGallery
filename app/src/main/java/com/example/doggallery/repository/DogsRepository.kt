package com.example.doggallery.repository

import android.content.Context
import android.util.Log
import com.example.doggallery.database.DogEntity
import com.example.doggallery.database.DogsDatabase
import com.example.doggallery.retrofit.Dog
import com.example.doggallery.retrofit.DogsInstance
import com.example.doggallery.retrofit.DogService

class DogsRepository(context: Context) {
    private var database: DogsDatabase? = null
    private val TAG: String = DogsRepository::class.java.simpleName

    init {
        database = DogsDatabase.getDatabase(context)
    }

    private val dogsService: DogService = DogsInstance.dogService

    suspend fun getRandomDogs(): List<Dog> {
        return dogsService.getRandomImages(20).message.map {
            Dog(image = it)
        }
    }

    suspend fun getImagesByBreed(breed: String): List<Dog> {
        println("Search by $breed")
       try {
           return dogsService.getImagesByBreed(breed, 20).message.map {
               Dog(image = it)
           }
       } catch (e: Exception) {
           Log.e(TAG, e.message!!)
           return emptyList()
       }
    }

    suspend fun addToFavorites(dog: Dog) {
        database!!.dogsDAO().insert(DogEntity(image = dog.image))
    }

    suspend fun deleteFromFavorites(dog: Dog) {
        database!!.dogsDAO().delete(dog.image)
    }

    suspend fun getFavorites(): List<Dog> {
        return database!!.dogsDAO().getFavorites().map {
            Dog(id = it.id, image = it.image, favorite = true)
        }
    }
}