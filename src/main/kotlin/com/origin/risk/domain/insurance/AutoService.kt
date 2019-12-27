package com.origin.risk.domain.insurance

import com.origin.risk.domain.customer.Customer
import com.origin.risk.domain.engine.RiskEngine
import com.origin.risk.domain.insurance.rules.HighIncomeRule
import com.origin.risk.domain.insurance.rules.LowAgeRule
import com.origin.risk.domain.insurance.rules.VehicleAgeRule
import com.origin.risk.domain.insurance.rules.VehicleRule

internal object AutoService {
    fun riskProfile(customer: Customer, riskQuestions: Array<Int>) =
        RiskEngine(customer, riskQuestions).riskProfile("auto", listOf(
            VehicleRule,
            LowAgeRule,
            HighIncomeRule,
            VehicleAgeRule))
}