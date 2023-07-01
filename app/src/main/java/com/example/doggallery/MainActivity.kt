package com.example.doggallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.doggallery.screens.FavoriteScreen
import com.example.doggallery.screens.MainScreen
import com.example.doggallery.ui.theme.DogGalleryTheme
import com.example.doggallery.utils.Routes
import com.example.doggallery.viewmodel.DogViewModel

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mViewModel: DogViewModel by viewModels {
            DogViewModel.Companion.Factory(this)
        }
        mViewModel.getDogsImages()
        mViewModel.getAllFavorites()

        setContent {
            DogGalleryTheme {
                NavigationController(mViewModel)
            }
        }
    }
}

@Composable
fun NavigationController(viewModel: DogViewModel) {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.MainScreen.route
    ) {
        composable(route = Routes.MainScreen.route) { MainScreen(navController = navController, viewModel) }
        composable(route = Routes.FavoriteScreen.route) { FavoriteScreen(navController = navController, viewModel) }
    }
}
