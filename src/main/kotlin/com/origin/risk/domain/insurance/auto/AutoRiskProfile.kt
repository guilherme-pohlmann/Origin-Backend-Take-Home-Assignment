package com.origin.risk.domain.insurance.auto

import com.origin.risk.domain.assets.Vehicle
import com.origin.risk.domain.engine.RiskProfile
import com.origin.risk.domain.engine.Score

class AutoRiskProfile(
        name: String = "",
        eligible: Boolean = false,
        score: Score = Score(0),
        val vehicle: Vehicle
) : RiskProfile(name, eligible, score)