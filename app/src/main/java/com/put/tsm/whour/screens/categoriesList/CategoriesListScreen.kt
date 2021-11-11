package com.put.tsm.whour.screens.categoriesList

import androidx.compose.foundation.layout.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.put.tsm.whour.data.models.Category
import com.put.tsm.whour.ui.theme.Roboto
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.res.stringResource
import com.put.tsm.whour.R
import com.put.tsm.whour.screens.RetrySection

@Composable
fun CategoriesListScreen(navController: NavController) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            CategoriesList(navController = navController)
        }
    }
}

@Composable
fun CategoriesList(
    navController: NavController,
    viewModel: CategoriesListViewModel = hiltViewModel()
) {
    val categoriesList by remember { viewModel.categoriesList }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }

    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        items(categoriesList) { category ->
            CategoryRow(navController = navController, category = category)
        }
    }

    Box(
        contentAlignment = Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }
        loadError?.let {
            RetrySection(error = it)
        }
    }
}

@Composable
fun CategoryRow(navController: NavController, category: Category) {
    Column {
        Button(
            onClick = {
                navController.navigate(
                    "details_screen/${category.id}"
                )
            },
            contentPadding = PaddingValues(
                start = 20.dp,
                top = 12.dp,
                end = 20.dp,
                bottom = 12.dp
            )
        ) {
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                text = category.name,
                fontFamily = Roboto,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
}