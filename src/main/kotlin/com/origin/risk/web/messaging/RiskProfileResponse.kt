package com.origin.risk.web.messaging

import com.origin.risk.domain.engine.RiskProfile
import com.origin.risk.domain.insurance.auto.AutoRiskProfile
import com.origin.risk.domain.insurance.disability.DisabilityRiskProfile
import com.origin.risk.domain.insurance.home.HomeRiskProfile
import com.origin.risk.domain.insurance.life.LifeRiskProfile

data class RiskProfileResponse(
    val auto: List<AutoRiskProfileResponse>,
    val disability: String,
    val home: String,
    val life: String
) {
    companion object {
        fun mapFromDomain(profiles: List<RiskProfile>) = RiskProfileResponse(
            auto = profiles.filterIsInstance<AutoRiskProfile>().map { AutoRiskProfileResponse(it.vehicle.key, it.status.name.toLowerCase()) },
            disability = profiles.filterIsInstance<DisabilityRiskProfile>().first().status.name.toLowerCase(),
            home = profiles.filterIsInstance<HomeRiskProfile>().first().status.name.toLowerCase(),
            life = profiles.filterIsInstance<LifeRiskProfile>().first().status.name.toLowerCase()
        )
    }
}

data class AutoRiskProfileResponse(
        val key: Int,
        val recommendation: String
)