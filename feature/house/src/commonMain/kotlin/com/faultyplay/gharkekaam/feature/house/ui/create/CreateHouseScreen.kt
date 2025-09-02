package com.faultyplay.gharkekaam.feature.house.ui.create

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.rickclephas.kmp.observableviewmodel.koin.getViewModel

@Composable
fun CreateHouseScreen(
    onHouseCreated: () -> Unit
) {
    val viewModel: CreateHouseViewModel = getViewModel()
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.isSuccess) {
        // Navigate back after success
        LaunchedEffect(Unit) {
            onHouseCreated()
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Create a New House", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = uiState.houseName,
                onValueChange = viewModel::onHouseNameChange,
                label = { Text("House Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = uiState.allowlistEmails,
                onValueChange = viewModel::onAllowlistChange,
                label = { Text("Invited Emails (comma-separated)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.isLoading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = viewModel::createHouse,
                    enabled = uiState.houseName.isNotBlank()
                ) {
                    Text("Create House")
                }
            }

            uiState.error?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            uiState.createdHouseCode?.let {
                Text("Share this code with your house members: $it")
            }
        }
    }
}
