package com.faultyplay.gharkekaam.core.data.util

object TaskIdGenerator {
    fun generateIdFromName(taskName: String): String {
        val sanitized = taskName.trim().lowercase()
        // Replace whitespace sequences with a single hyphen
        val withHyphens = sanitized.replace(Regex("\\s+"), "-")
        // Remove all characters that are not lowercase letters, numbers, or hyphens
        return withHyphens.replace(Regex("[^a-z0-9-]"), "")
    }
}
