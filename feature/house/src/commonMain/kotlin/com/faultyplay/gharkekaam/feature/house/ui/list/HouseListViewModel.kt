package com.faultyplay.gharkekaam.feature.house.ui.list

import com.rickclephas.kmp.observableviewmodel.ViewModel
import com.rickclephas.kmp.observableviewmodel.launch
import com.faultyplay.gharkekaam.core.data.model.House
import com.faultyplay.gharkekaam.core.data.repository.AuthRepository
import com.faultyplay.gharkekaam.core.data.repository.HouseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class HouseListUiState(
    val houses: List<House> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)

class HouseListViewModel(
    private val houseRepository: HouseRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HouseListUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadUserHouses()
    }

    fun loadUserHouses() {
        launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            val currentUser = authRepository.getCurrentUser()
            if (currentUser == null) {
                _uiState.update { it.copy(isLoading = false, error = "User not signed in.") }
                return@launch
            }

            val result = houseRepository.getUserHouses(currentUser.uid)
            result.onSuccess { houses ->
                _uiState.update { it.copy(isLoading = false, houses = houses) }
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false, error = error.message) }
            }
        }
    }
}
