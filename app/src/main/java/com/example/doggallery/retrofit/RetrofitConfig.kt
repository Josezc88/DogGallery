package com.example.doggallery.retrofit

import com.google.gson.Gson
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL: String = "https://dog.ceo/api/"

object DogsInstance {
    private val retrofit: Retrofit by lazy  {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
    }

    val dogService: DogService by lazy {
        retrofit.create(DogService::class.java)
    }
}

