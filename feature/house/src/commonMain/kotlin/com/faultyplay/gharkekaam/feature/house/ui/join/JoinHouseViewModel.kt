package com.faultyplay.gharkekaam.feature.house.ui.join

import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.launch
import com.faultyplay.gharkekaam.core.data.repository.AuthRepository
import com.faultyplay.gharkekaam.core.data.repository.HouseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class JoinHouseUiState(
    val houseCode: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

class JoinHouseViewModel(
    private val houseRepository: HouseRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(JoinHouseUiState())
    val uiState = _uiState.asStateFlow()

    fun onHouseCodeChange(code: String) {
        _uiState.update { it.copy(houseCode = code) }
    }

    fun joinHouse() {
        viewModelScope.launch {
            val currentUser = authRepository.getCurrentUser()
            if (currentUser == null || currentUser.email == null) {
                _uiState.update { it.copy(error = "You must be signed in to join a house.") }
                return@launch
            }

            _uiState.update { it.copy(isLoading = true, error = null) }

            val result = houseRepository.joinHouse(
                houseCode = _uiState.value.houseCode,
                userId = currentUser.uid,
                userEmail = currentUser.email?: ""
            )

            result.onSuccess {
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false, error = error.message) }
            }
        }
    }
}
