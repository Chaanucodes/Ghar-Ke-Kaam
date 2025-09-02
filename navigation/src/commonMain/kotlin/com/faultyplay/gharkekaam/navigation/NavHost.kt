package com.faultyplay.gharkekaam.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.faultyplay.gharkekaam.feature.auth.ui.AuthScreen
import com.faultyplay.gharkekaam.feature.house.ui.create.CreateHouseScreen
import com.faultyplay.gharkekaam.feature.house.ui.join.JoinHouseScreen
import com.faultyplay.gharkekaam.feature.house.ui.list.HouseListScreen
import org.koin.compose.koinInject

@Composable
fun NavHost() {
    val navigator: Navigator = koinInject()
    val currentScreen by navigator.currentScreen.collectAsState()

    when (currentScreen) {
        is Screen.Auth -> {
            AuthScreen(
                onSignInSuccess = {
                    navigator.replaceAllWith(Screen.HouseList)
                }
            )
        }
        is Screen.HouseList -> {
            HouseListScreen(
                onCreateHouseClicked = { navigator.navigateTo(Screen.CreateHouse) },
                onJoinHouseClicked = { navigator.navigateTo(Screen.JoinHouse) },
                onHouseClicked = { /* Navigate to house details later */ }
            )
        }
        is Screen.CreateHouse -> {
            CreateHouseScreen(
                onHouseCreated = { navigator.goBack() }
            )
        }
        is Screen.JoinHouse -> {
            JoinHouseScreen(
                onHouseJoined = { navigator.goBack() }
            )
        }
    }
}
