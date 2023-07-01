package com.example.doggallery.models

import com.example.doggallery.database.DogEntity
import com.example.doggallery.retrofit.Dog

data class DogState(
    val dogs: List<Dog> = emptyList(),
    val valueSearch: String = "",
    val image: String = "",
    val id: Int = 0
)