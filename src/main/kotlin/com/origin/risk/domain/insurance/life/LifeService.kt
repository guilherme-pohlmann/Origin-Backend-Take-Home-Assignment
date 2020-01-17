package com.origin.risk.domain.insurance.life

import com.origin.risk.domain.customer.Customer
import com.origin.risk.domain.engine.RiskEngine
import com.origin.risk.domain.insurance.rules.*

internal object LifeService {
    fun riskProfile(customer: Customer, riskQuestions: Array<Int>) =
        RiskEngine(customer, riskQuestions).riskProfile("life", listOf(
            HighAgeRule,
            LowAgeRule,
            HighIncomeRule,
            DependentsRule,
            LifeInsuranceMarriedRule)).let {
            LifeRiskProfile(
                    it.name,
                    it.eligible,
                    it.score
            )
        }
}