package com.example.doggallery.retrofit

data class Breeds (
    val message: Map<String, List<String>>,
    val status: String
)

data class Breed(
    val message: List<String>,
    val status: String,
    val code: Int? = 0
)

/*data class Dog (
    val message: String,
    val status: String
)*/

data class Dogs (
    val message: List<String>,
    val status: String
)

data class Dog (
    val id: Int = 0,
    val image: String,
    var favorite: Boolean? = false
)