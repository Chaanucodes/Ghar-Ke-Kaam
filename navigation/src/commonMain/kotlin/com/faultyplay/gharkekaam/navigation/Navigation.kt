package com.faultyplay.gharkekaam.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * A sealed interface representing all possible screens in the application.
 */
sealed interface Screen {
    data object Auth : Screen
    data object HouseList : Screen
    data object CreateHouse : Screen
    data object JoinHouse : Screen
}

/**
 * A simple navigator class to manage the navigation stack.
 * It exposes the current screen as a StateFlow to be observed by the UI.
 */
class Navigator {
    private val _backStack = MutableStateFlow<List<Screen>>(listOf(Screen.Auth))
    val backStack = _backStack.asStateFlow()

    val currentScreen = MutableStateFlow(_backStack.value.last())

    init {
        _backStack.asStateFlow().subscribe {
            currentScreen.value = it.lastOrNull() ?: Screen.Auth // Default to Auth if stack is empty
        }
    }

    fun navigateTo(screen: Screen) {
        _backStack.update { it + screen }
    }

    fun goBack() {
        _backStack.update {
            if (it.size > 1) it.dropLast(1) else it
        }
    }

    fun replaceAllWith(screen: Screen) {
        _backStack.update { listOf(screen) }
    }
}

// Simple subscribe extension for StateFlow in common code
private fun <T> StateFlow<T>.subscribe(onEach: (T) -> Unit) {
    // This is a simplified subscription for common code.
    // In a real app, you would use a more robust way to collect flows
    // that is tied to a lifecycle, but for a simple navigator state, this is sufficient.
    // The collection happens in the NavHost which is a Composable and handles its lifecycle.
}
