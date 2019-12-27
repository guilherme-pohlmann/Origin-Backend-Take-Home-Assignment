package com.origin.risk.web.messaging

import com.origin.risk.domain.engine.RiskProfile

data class RiskProfileResponse(
    val auto: String,
    val disability: String,
    val home: String,
    val life: String
) {
    companion object {
        fun mapFromDomain(profiles: List<RiskProfile>) = RiskProfileResponse(
            auto = profiles.first { it.name == "auto" }.status.name.toLowerCase(),
            disability = profiles.first { it.name == "disability" }.status.name.toLowerCase(),
            home = profiles.first { it.name == "home" }.status.name.toLowerCase(),
            life = profiles.first { it.name == "life" }.status.name.toLowerCase()
        )
    }
}