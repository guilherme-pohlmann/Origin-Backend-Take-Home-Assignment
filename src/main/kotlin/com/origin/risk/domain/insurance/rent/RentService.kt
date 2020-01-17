package com.origin.risk.domain.insurance.rent

import com.origin.risk.domain.customer.Customer
import com.origin.risk.domain.engine.RiskEngine
import com.origin.risk.domain.insurance.rules.*

internal object RentService {
    fun riskProfile(customer: Customer, riskQuestions: Array<Int>) =
            RiskEngine(customer, riskQuestions).riskProfile("rent", listOf(
                    HouseRule,
                    LowAgeRule,
                    HighIncomeRule)).let {
                RentRiskProfile(
                        it.name,
                        it.eligible,
                        it.score
                )
            }
}