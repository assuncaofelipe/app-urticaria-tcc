package com.assuncao.ufsc.urticaria.domain.entity

data class Urticaria(
    val id: Long = 0,
    val description: String,
    val severity: Int,
    val timestamp: Long,
)
