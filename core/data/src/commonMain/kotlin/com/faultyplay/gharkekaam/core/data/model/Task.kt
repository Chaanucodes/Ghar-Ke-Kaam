package com.faultyplay.gharkekaam.core.data.model

import dev.gitlive.firebase.Timestamp
import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val taskId: String, // Generated from taskName
    val houseId: String,
    val taskName: String,
    val lastCompletionDate: Timestamp? = null
)
