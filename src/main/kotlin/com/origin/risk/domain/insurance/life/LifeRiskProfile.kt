package com.origin.risk.domain.insurance.life

import com.origin.risk.domain.engine.ProfileStatus
import com.origin.risk.domain.engine.RiskProfile
import com.origin.risk.domain.engine.Score

class LifeRiskProfile(
        name: String = "",
        eligible: Boolean = false,
        score: Score = Score(0)
) : RiskProfile(name, eligible, score, ::lifeInsuranceProfileStatus) {
    companion object {
        internal fun lifeInsuranceProfileStatus(riskProfile: RiskProfile): ProfileStatus {
            val baseStatus = baseProfileStatus(riskProfile)

            if(baseStatus == ProfileStatus.INELIGIBLE)
                return baseStatus

            return when {
                riskProfile.score.value <= 0 -> ProfileStatus.REGULAR
                riskProfile.score.value in (1..2) -> ProfileStatus.ECONOMIC
                else -> ProfileStatus.RESPONSIBLE
            }
        }
    }
}

