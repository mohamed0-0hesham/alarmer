package com.hesham0_0.alarmer.domain.model

data class PersistentTask(
    val id: String,
    val title: String,
    val description: String,
    val isDone: Boolean = false,
    val isSticky: Boolean = true // Disappears only when confirmed
)
