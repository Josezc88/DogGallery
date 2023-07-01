package com.example.doggallery.utils

sealed class Routes(val route: String) {
    object MainScreen: Routes("main_screen")
    object FavoriteScreen: Routes("favorite_screen")
}