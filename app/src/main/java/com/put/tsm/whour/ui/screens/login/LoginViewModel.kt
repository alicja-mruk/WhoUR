package com.put.tsm.whour.ui.screens.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.put.tsm.whour.data.models.Gender
import com.put.tsm.whour.data.repository.usecases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {
    val isLoading = mutableStateOf(false)

    private val _eventsFlow = MutableSharedFlow<LoginUiEvent>()
    val eventsFlow: SharedFlow<LoginUiEvent> get() = _eventsFlow

    sealed class LoginUiEvent {
        object Idle : LoginUiEvent()
        object LoginSuccess : LoginUiEvent()
    }

    fun login(name: String, age: Int, gender: Gender) {
        viewModelScope.launch {
            isLoading.value = true
            loginUseCase(name, age, gender)
            _eventsFlow.emit(LoginUiEvent.LoginSuccess)
            isLoading.value = false
        }
    }
}