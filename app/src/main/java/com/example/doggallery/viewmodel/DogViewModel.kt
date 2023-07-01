package com.example.doggallery.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.doggallery.models.DogState
import com.example.doggallery.repository.DogsRepository
import com.example.doggallery.retrofit.Dog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DogViewModel(context: Context): ViewModel() {
    private val couroutineScope: CoroutineScope = viewModelScope
    private var repository: DogsRepository? = null
    var state by mutableStateOf(DogState())
    var favoritesState by mutableStateOf(DogState())

    init {
        repository = DogsRepository(context)
    }

    fun getDogsImages() {
        couroutineScope.launch(Dispatchers.IO) {
            repository!!.getRandomDogs().also {
                state = state.copy(dogs = state.dogs.plus(it))
            }
        }
    }

    fun addToFavorites(dog: Dog) {
        couroutineScope.launch(Dispatchers.IO) {
            repository!!.addToFavorites(dog)
            getAllFavorites()
        }
    }

    fun deleteFromFavorites(dog: Dog) {
        couroutineScope.launch(Dispatchers.IO) {
            repository!!.deleteFromFavorites(dog)
            getAllFavorites()
        }
    }

    fun getAllFavorites() {
        couroutineScope.launch(Dispatchers.IO) {
            repository!!.getFavorites().also {
                println("FAV: " + it.size)
                favoritesState = favoritesState.copy(dogs = it)
            }
        }
    }

    fun searchByBreed() {
        couroutineScope.launch(Dispatchers.IO) {
            repository!!.getImagesByBreed(state.valueSearch).also {
                state = state.copy(dogs = it)
            }
        }
    }

    fun changeValueState(search: String) {
        state = state.copy(valueSearch = search)
    }

    companion object {
        class Factory(val context: Context): ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DogViewModel(context) as T
            }
        }
    }
}