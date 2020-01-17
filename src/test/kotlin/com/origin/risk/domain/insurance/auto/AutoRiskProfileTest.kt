package com.origin.risk.domain.insurance.auto

import com.origin.risk.domain.assets.Vehicle
import com.origin.risk.domain.engine.ProfileStatus
import com.origin.risk.domain.engine.Score
import org.junit.jupiter.api.Test

class AutoRiskProfileTest {
    @Test
    fun `profile status should be INELIGIBLE when RiskProfile is inelegible`() {
        val profile = AutoRiskProfile("test", false, Score(3), Vehicle(0, 2010))

        assert(profile.status == ProfileStatus.INELIGIBLE)
    }

    @Test
    fun `profile status should be ECONOMIC when Score less then or equal to 0`() {
        val profile = AutoRiskProfile("test", true, Score(0), Vehicle(0, 2010))
        assert(profile.status == ProfileStatus.ECONOMIC)

        val newProfile = profile.deduct(2)
        assert(newProfile.status == ProfileStatus.ECONOMIC)
    }

    @Test
    fun `profile status should be REGULAR when Score is equal to 1 or 2`() {
        val profile = AutoRiskProfile("test", true, Score(1), Vehicle(0, 2010))
        assert(profile.status == ProfileStatus.REGULAR)

        val newProfile = profile.add(1)
        assert(newProfile.status == ProfileStatus.REGULAR)
    }

    @Test
    fun `profile status should be RESPONSIBLE when Score greater then or equal to 3`() {
        val profile = AutoRiskProfile("test", true, Score(3),Vehicle(0, 2010))
        assert(profile.status == ProfileStatus.RESPONSIBLE)

        val newProfile = profile.add(2)
        assert(newProfile.status == ProfileStatus.RESPONSIBLE)
    }
}