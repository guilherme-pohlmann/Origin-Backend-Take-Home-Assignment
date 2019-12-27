package com.origin.risk.domain.engine

data class Score(
    val value: Int
) {
    fun subtract(value: Int) = Score(this.value - value)

    fun add(value: Int) = Score(this.value + value)
}