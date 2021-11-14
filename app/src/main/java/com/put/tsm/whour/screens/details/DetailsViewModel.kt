package com.put.tsm.whour.screens.details

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.put.tsm.whour.data.models.CategoryItem
import com.put.tsm.whour.data.repository.usecases.GetItemsFromCategoryUseCase
import com.put.tsm.whour.data.repository.usecases.SaveQuizResultUseCase
import com.put.tsm.whour.data.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getItemsFromCategory: GetItemsFromCategoryUseCase,
    private val saveQuizResult: SaveQuizResultUseCase,
    savedStateHandle: SavedStateHandle
) :
    ViewModel() {
    private var categoryId: String = savedStateHandle.get(CATEGORY_ID_KEY) ?: DEFAULT_CATEGORY_ID
    private var index0ItemList: List<CategoryItem> = emptyList()
    private var index1ItemList: List<CategoryItem> = emptyList()

    val index0Item = mutableStateOf<CategoryItem?>(null)
    val index1Item = mutableStateOf<CategoryItem?>(null)
    private val itemsAnswered = mutableStateOf(0)

    val isEmpty = mutableStateOf(false)
    val isLoading = mutableStateOf(false)
    val loadError = mutableStateOf<Exception?>(null)

    private val answers: MutableList<String> = mutableListOf()
    private var listMaxIndex: Int = 0

    private val _eventsFlow = MutableSharedFlow<DetailsUiEvent>()
    val eventsFlow: SharedFlow<DetailsUiEvent> get() = _eventsFlow

    sealed class DetailsUiEvent {
        object Idle : DetailsUiEvent()
        class QuizFinished(val winnerType: String) : DetailsUiEvent()
    }

    private fun sendEvent(event: DetailsUiEvent) {
        viewModelScope.launch {
            _eventsFlow.emit(event)
        }
    }

    fun goNext(type: String) {
        answers.add(type)

        if (itemsAnswered.value == listMaxIndex) {
            viewModelScope.launch {
                val winnerType = answers.groupBy { it }
                    .maxByOrNull { it.value.size }
                    ?.key ?: return@launch
                saveQuizResult(categoryId, winnerType)
                sendEvent(DetailsUiEvent.QuizFinished(winnerType))
            }
        } else {
            itemsAnswered.value = itemsAnswered.value + 1
            index0Item.value = index0ItemList[itemsAnswered.value]
            index1Item.value = index1ItemList[itemsAnswered.value]
        }
    }

    companion object {
        const val FIRST_ITEM_GROUP: Long = 0
        const val SECOND_ITEM_GROUP: Long = 1
        const val CATEGORY_ID_KEY: String = "categoryId"
        const val DEFAULT_CATEGORY_ID: String = "Default category id"
    }

    init {
        viewModelScope.launch {
            isLoading.value = true
            when (val result = getItemsFromCategory(categoryId)) {
                is Result.Success -> {
                    if (result.data.isEmpty()){
                        isEmpty.value = true
                        return@launch
                    }
                    listMaxIndex = (result.data.size / 2) - 1
                    index0ItemList = result.data.filter { it.index == FIRST_ITEM_GROUP }
                    index1ItemList = result.data.filter { it.index == SECOND_ITEM_GROUP }
                    index0Item.value = index0ItemList[itemsAnswered.value]
                    index1Item.value = index1ItemList[itemsAnswered.value]
                }
                is Result.Error -> loadError.value = result.exception
            }
            isLoading.value = false
        }
    }
}