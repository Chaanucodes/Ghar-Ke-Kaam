package com.faultyplay.gharkekaam.feature.house.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rickclephas.kmp.observableviewmodel.koin.getViewModel

@Composable
fun HouseListScreen(
    onCreateHouseClicked: () -> Unit,
    onJoinHouseClicked: () -> Unit,
    onHouseClicked: (houseId: String) -> Unit
) {
    val viewModel: HouseListViewModel = getViewModel()
    val uiState by viewModel.uiState.collectAsState()

    Surface(modifier = Modifier.fillMaxSize()) {
        if (uiState.isLoading) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator()
            }
        } else if (uiState.error != null) {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                Text("Error: ${uiState.error}")
            }
        } else if (uiState.houses.isEmpty()) {
            // Empty state
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("No houses found.")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onCreateHouseClicked) {
                    Text("Create a House")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onJoinHouseClicked) {
                    Text("Join a House")
                }
            }
        } else {
            // List of houses
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Your Houses", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                uiState.houses.forEach { house ->
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        onClick = { onHouseClicked(house.houseId) }
                    ) {
                        Text(house.name, modifier = Modifier.padding(16.dp))
                    }
                }
            }
        }
    }
}
