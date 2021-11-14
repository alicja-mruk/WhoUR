package com.put.tsm.whour.ui.screens.splashScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.put.tsm.whour.data.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val repository: FirebaseRepository) : ViewModel() {
    val isLoggedIn = mutableStateOf(false)

    init {
        viewModelScope.launch {
            repository.userFlow.collect {
                isLoggedIn.value = it != null
            }
        }
    }
}