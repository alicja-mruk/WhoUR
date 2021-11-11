package com.put.tsm.whour.screens.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.put.tsm.whour.data.models.CategoryItem
import com.put.tsm.whour.data.repository.usecases.GetItemsFromCategoryUseCase
import com.put.tsm.whour.data.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val getItemsFromCategory: GetItemsFromCategoryUseCase) :
    ViewModel() {
    private var index0ItemList: List<CategoryItem> = emptyList()
    private var index1ItemList: List<CategoryItem> = emptyList()

    val index0Item = mutableStateOf<CategoryItem?>(null)
    val index1Item = mutableStateOf<CategoryItem?>(null)
    val itemsAnswered = mutableStateOf<Int>(0)

    val isLoading = mutableStateOf(false)
    val loadError = mutableStateOf<Exception?>(null)

    fun init(categoryId: String) {
        viewModelScope.launch {
            isLoading.value = true
            when (val result = getItemsFromCategory(categoryId)) {
                is Result.Success -> {
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

    fun goNext(index: Int) {
        if (itemsAnswered.value == LIST_MAX_INDEX) {
            // todo:  finish quiz and make summary
            return
        }

        // save result
        itemsAnswered.value = itemsAnswered.value + 1
    }

    companion object {
        const val FIRST_ITEM_GROUP: Int = 0
        const val SECOND_ITEM_GROUP: Int = 0
        const val LIST_MAX_INDEX: Int = 9
    }
}