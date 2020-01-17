package com.origin.risk.domain.insurance.auto

import com.origin.risk.domain.customer.Customer
import com.origin.risk.domain.engine.RiskEngine
import com.origin.risk.domain.insurance.rules.HighIncomeRule
import com.origin.risk.domain.insurance.rules.LowAgeRule
import com.origin.risk.domain.insurance.rules.VehicleAgeRule
import com.origin.risk.domain.insurance.rules.VehicleRule

internal object AutoService {
    fun riskProfile(customer: Customer, riskQuestions: Array<Int>) = sequence {
        val riskEngine = RiskEngine(customer, riskQuestions)

        val eligibleProfile = riskEngine.riskProfile("auto", listOf(VehicleRule))

        if(!eligibleProfile.eligible) {
            yield(eligibleProfile)
        }

        customer.vehicles.forEach { vehicle ->
            val riskProfile = riskEngine.riskProfile("auto", listOf(
                    LowAgeRule,
                    HighIncomeRule,
                    VehicleAgeRule(vehicle)))

            yield(AutoRiskProfile(
                    riskProfile.name,
                    riskProfile.eligible,
                    riskProfile.score,
                    vehicle
            ))
        }
    }
}