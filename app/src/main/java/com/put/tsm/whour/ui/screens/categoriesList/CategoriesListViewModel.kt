package com.put.tsm.whour.ui.screens.categoriesList

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.put.tsm.whour.data.models.Category
import com.put.tsm.whour.data.repository.usecases.GetAllCategoriesUseCase
import com.put.tsm.whour.data.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesListViewModel @Inject constructor(private val getAllCategories: GetAllCategoriesUseCase) :
    ViewModel() {

    val categoriesList = mutableStateOf<List<Category>>(listOf())
    val isLoading = mutableStateOf(false)
    val loadError = mutableStateOf<Exception?>(null)

    init {
        viewModelScope.launch {
            isLoading.value = true
            when (val result = getAllCategories()) {
                is Result.Success -> categoriesList.value = result.data
                is Result.Error -> loadError.value = result.exception
            }
            isLoading.value = false
        }
    }
}