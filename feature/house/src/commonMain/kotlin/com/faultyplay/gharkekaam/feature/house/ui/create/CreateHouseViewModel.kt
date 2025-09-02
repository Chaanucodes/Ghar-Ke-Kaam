package com.faultyplay.gharkekaam.feature.house.ui.create

import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.launch
import com.faultyplay.gharkekaam.core.data.repository.AuthRepository
import com.faultyplay.gharkekaam.core.data.repository.HouseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CreateHouseUiState(
    val houseName: String = "",
    val allowlistEmails: String = "", // Comma-separated
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
    val createdHouseCode: String? = null
)

class CreateHouseViewModel(
    private val houseRepository: HouseRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateHouseUiState())
    val uiState = _uiState.asStateFlow()

    fun onHouseNameChange(name: String) {
        _uiState.update { it.copy(houseName = name) }
    }

    fun onAllowlistChange(emails: String) {
        _uiState.update { it.copy(allowlistEmails = emails) }
    }

    fun createHouse() {
        launch {
            val currentUser = authRepository.getCurrentUser()
            if (currentUser == null) {
                _uiState.update { it.copy(error = "You must be signed in to create a house.") }
                return@launch
            }

            _uiState.update { it.copy(isLoading = true, error = null) }

            val emails = _uiState.value.allowlistEmails.split(',').map { it.trim() }.filter { it.isNotBlank() }

            val result = houseRepository.createHouse(
                houseName = _uiState.value.houseName,
                creatorId = currentUser.uid,
                allowlist = emails
            )

            result.onSuccess { code ->
                _uiState.update { it.copy(isLoading = false, isSuccess = true, createdHouseCode = code) }
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false, error = error.message) }
            }
        }
    }
}
