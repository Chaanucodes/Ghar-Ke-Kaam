package com.faultyplay.gharkekaam.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class House(
    val houseId: String,
    val name: String,
    val members: List<String> = emptyList(), // List of user UIDs
    val allowlist: List<String> = emptyList() // List of emails for invites
)
