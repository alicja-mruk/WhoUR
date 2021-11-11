package com.put.tsm.whour.screens.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.put.tsm.whour.screens.categoriesList.CategoriesListViewModel
import timber.log.Timber

@Composable
fun DetailsScreen(
    categoryId: String,
    navController: NavHostController,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    viewModel.init(categoryId)

    val index0Item by remember { viewModel.index0Item }
    val index1Item by remember { viewModel.index1Item }
    val loadError by remember { viewModel.loadError }
    val isLoading by remember { viewModel.isLoading }
}