package com.put.tsm.whour.screens.categoriesList

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.put.tsm.whour.data.models.Category
import com.put.tsm.whour.data.repository.usecases.GetAllCategoriesUseCase
import com.put.tsm.whour.data.repository.usecases.GetAllItemsUseCase
import com.put.tsm.whour.data.repository.usecases.GetItemsFromCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesListViewModel @Inject constructor(private val getAllItemsFromCategory: GetItemsFromCategoryUseCase): ViewModel() {
    var categoriesList = mutableStateOf<List<Category>>(listOf())

    init {
        viewModelScope.launch{
            val items = getAllItemsFromCategory("categoryAnimals")
            val d = 0
        }

    }
}