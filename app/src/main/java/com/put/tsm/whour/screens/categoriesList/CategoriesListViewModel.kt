package com.put.tsm.whour.screens.categoriesList

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.put.tsm.whour.data.models.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoriesListViewModel @Inject constructor(): ViewModel() {
    var categoriesList = mutableStateOf<List<Category>>(listOf())

}