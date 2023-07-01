package com.example.doggallery.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.List
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.doggallery.retrofit.Dog
import com.example.doggallery.utils.Routes
import com.example.doggallery.viewmodel.DogViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController, mViewModel: DogViewModel) {
    val state = mViewModel.state
    val context = LocalContext.current
    val scrollState = rememberLazyGridState()
    val isScrollAtBottom = !scrollState.canScrollForward

    LaunchedEffect(isScrollAtBottom) {
        if (isScrollAtBottom) {
            if (state.valueSearch.isEmpty()) {
                mViewModel.getDogsImages()
            } else {
                mViewModel.searchByBreed()
            }
        }
    }

    if (state.dogs.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Rounded.List,
                contentDescription = "No hay elementos",
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp),
                tint = Color.LightGray
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "No hay elementos que coincidan con el termino que ingresaste",
                fontWeight = FontWeight.Medium,
                fontSize = TextUnit(16f, TextUnitType.Sp),
                textAlign = TextAlign.Center,
                color = Color.LightGray
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            value = state.valueSearch,
            trailingIcon = {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            },
            onValueChange = {
                mViewModel.changeValueState(it)
                if (it.isEmpty()) {
                    mViewModel.getDogsImages()
                } else {
                    mViewModel.searchByBreed()
                }
            },
            textStyle = TextStyle(Color.DarkGray, fontStyle = FontStyle.Normal, fontWeight = FontWeight.Medium),
            label = {
                Text(text = "Ingresa el nombre de una raza")
            },
            placeholder = {
                Text(text = "Ingresa el nombre de una raza")
            }
        )
        Spacer(modifier = Modifier.height(20.dp))
        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            state = scrollState
        ) {
            items(state.dogs) {
                DogCard(it) {
                    println("Click!")
                    if (it.favorite == true) {
                        mViewModel.deleteFromFavorites(it)
                        showToast(context, "La imagen se ha eliminado de tus favoritos")
                    } else {
                        mViewModel.addToFavorites(it)
                        showToast(context, "La imagen se ha agregado de tus favoritos")
                    }

                    navController.navigate(Routes.FavoriteScreen.route)
                }
            }
        }
    }
}

@Composable
fun DogCard(dog: Dog, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(10.dp),
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(1f)
        ) {
            AsyncImage(
                model = dog.image,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .heightIn(120.dp)
                    .fillMaxSize()
            )
            IconButton(
                onClick = onClick,
            ) {
                Icon(
                    imageVector = if (dog.favorite == true) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite",
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .padding(8.dp),
                    tint = if (dog.favorite == true) Color.Red else Color.White
                )
            }
        }
    }
}

fun showToast(context: Context, message: String) {
    Toast.makeText(
        context,
        message,
        Toast.LENGTH_SHORT
    ).show()
}
