package com.origin.risk.domain.insurance.rent

import com.origin.risk.domain.engine.RiskProfile
import com.origin.risk.domain.engine.Score

class RentRiskProfile(
        name: String = "",
        eligible: Boolean = false,
        score: Score = Score(0)
) : RiskProfile(name, eligible, score)