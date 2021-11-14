package com.put.tsm.whour.ui.screens.categoriesList

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.put.tsm.whour.data.models.Category
import com.put.tsm.whour.data.repository.FirebaseRepository
import com.put.tsm.whour.data.repository.usecases.GetAllCategoriesUseCase
import com.put.tsm.whour.data.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesListViewModel @Inject constructor(
    private val getAllCategories: GetAllCategoriesUseCase,
    private val repository: FirebaseRepository
) :
    ViewModel() {

    val categoriesList = mutableStateOf<List<Category>>(listOf())
    val isLoading = mutableStateOf(false)
    val loadError = mutableStateOf<Exception?>(null)

    private val _eventsFlow = MutableSharedFlow<CategoriesListUiEvent>()
    val eventsFlow: SharedFlow<CategoriesListUiEvent> get() = _eventsFlow

    sealed class CategoriesListUiEvent {
        object Idle : CategoriesListUiEvent()
        object LogoutSuccess : CategoriesListUiEvent()
    }

    fun logout() {
        viewModelScope.launch {
            repository.logout()
            _eventsFlow.emit(CategoriesListUiEvent.LogoutSuccess)
        }
    }

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