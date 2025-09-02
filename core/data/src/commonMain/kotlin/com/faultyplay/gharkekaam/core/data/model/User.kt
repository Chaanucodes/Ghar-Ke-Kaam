package com.faultyplay.gharkekaam.core.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val uid: String,
    val name: String? = null,
    val email: String? = null
)
