package com.origin.risk.domain.engine

import org.junit.jupiter.api.Test

class RiskProfileTest {
    @Test
    fun `RiskProfile should be a value object`() {
        assert(RiskProfile::class.isData)
    }

    @Test
    fun `ineligible should de immutable`() {
        val profile = RiskProfile("test", true, Score(3))
        val newProfile = profile.ineligible()

        assert(profile !== newProfile)
        assert(!newProfile.eligible)
    }

    @Test
    fun `deduct should de immutable`() {
        val profile = RiskProfile("test", true, Score(3))
        val newProfile = profile.deduct(2)

        assert(profile !== newProfile)
        assert(newProfile.score.value == 1)
    }

    @Test
    fun `add should de immutable`() {
        val profile = RiskProfile("test", true, Score(3))
        val newProfile = profile.add(2)

        assert(profile !== newProfile)
        assert(newProfile.score.value == 5)
    }

    @Test
    fun `profile status should be INELIGIBLE when RiskProfile is inelegible`() {
        val profile = RiskProfile("test", false, Score(3))

        assert(profile.status == ProfileStatus.INELIGIBLE)
    }

    @Test
    fun `profile status should be ECONOMIC when Score less then or equal to 0`() {
        val profile = RiskProfile("test", true, Score(0))
        assert(profile.status == ProfileStatus.ECONOMIC)

        val newProfile = profile.deduct(2)
        assert(newProfile.status == ProfileStatus.ECONOMIC)
    }

    @Test
    fun `profile status should be REGULAR when Score is equal to 1 or 2`() {
        val profile = RiskProfile("test", true, Score(1))
        assert(profile.status == ProfileStatus.REGULAR)

        val newProfile = profile.add(1)
        assert(newProfile.status == ProfileStatus.REGULAR)
    }

    @Test
    fun `profile status should be RESPONSIBLE when Score greater then or equal to 3`() {
        val profile = RiskProfile("test", true, Score(3))
        assert(profile.status == ProfileStatus.RESPONSIBLE)

        val newProfile = profile.add(2)
        assert(newProfile.status == ProfileStatus.RESPONSIBLE)
    }
}