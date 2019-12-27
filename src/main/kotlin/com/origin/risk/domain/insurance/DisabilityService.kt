package com.origin.risk.domain.insurance

import com.origin.risk.domain.customer.Customer
import com.origin.risk.domain.engine.RiskEngine
import com.origin.risk.domain.insurance.rules.*

internal object DisabilityService {
    fun riskProfile(customer: Customer, riskQuestions: Array<Int>) =
        RiskEngine(customer, riskQuestions).riskProfile("disability", listOf(
            IncomeRule,
            HighAgeRule,
            LowAgeRule,
            HighIncomeRule,
            MortgagedHouseRule,
            DependentsRule,
            DisabilityInsuranceMarriedRule))
}