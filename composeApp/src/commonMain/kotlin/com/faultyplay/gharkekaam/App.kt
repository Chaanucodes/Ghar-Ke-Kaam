package com.faultyplay.gharkekaam

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.faultyplay.gharkekaam.navigation.NavHost

@Composable
fun App() {
    MaterialTheme {
        Surface {
            NavHost()
        }
    }
}