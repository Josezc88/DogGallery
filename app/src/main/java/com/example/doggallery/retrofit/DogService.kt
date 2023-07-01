package com.example.doggallery.retrofit

import retrofit2.http.GET
import retrofit2.http.Path

interface DogService {
    @GET("breeds/list/all")
    suspend fun getAllBreeds(): Breeds

    @GET("breeds/image/random")
    suspend fun getRandomImage(): Dog

    @GET("breeds/image/random/{count}") // MAX 50
    suspend fun getRandomImages(@Path("count") count: Int): Dogs

    @GET("breed/{breed}/images")
    suspend fun getImagesByBreed(@Path("breed") breed: String): Breed

    @GET("breed/{breed}/images/random/{count}")
    suspend fun getImagesByBreed(@Path("breed") breed: String, @Path("count") count: Int): Breed

    @GET("breed/{breed}/list")
    suspend fun getSubBreeds(@Path("breed") breed: String): Breed
}