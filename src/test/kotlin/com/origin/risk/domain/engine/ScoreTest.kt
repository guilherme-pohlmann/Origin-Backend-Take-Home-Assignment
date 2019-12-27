package com.origin.risk.domain.engine

import org.junit.jupiter.api.Test

class ScoreTest {
    @Test
    fun `Score should be a value object`() {
        assert(Score::class.isData)
    }

    @Test
    fun `subtract should be immutable`() {
        val score = Score(3)
        val newScore = score.subtract(1)

        assert(score !== newScore)
        assert(newScore.value == 2)
    }

    @Test
    fun `add should be immutable`() {
        val score = Score(3)
        val newScore = score.add(1)

        assert(score !== newScore)
        assert(newScore.value == 4)
    }
}