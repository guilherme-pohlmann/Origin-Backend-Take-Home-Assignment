package com.origin.risk.domain.insurance

import com.origin.risk.domain.customer.Customer
import com.origin.risk.domain.engine.RiskEngine
import com.origin.risk.domain.insurance.rules.*

internal object HomeService {
    fun riskProfile(customer: Customer, riskQuestions: Array<Int>) =
        RiskEngine(customer, riskQuestions).riskProfile("home", listOf(
            HouseRule,
            LowAgeRule,
            HighIncomeRule,
            MortgagedHouseRule))
}