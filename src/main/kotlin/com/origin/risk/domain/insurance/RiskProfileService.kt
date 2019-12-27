package com.origin.risk.domain.insurance

import com.origin.risk.domain.customer.Customer

object RiskProfileService {
    fun riskProfile(customer: Customer, riskQuestions: Array<Int>) =
        listOf(
            AutoService.riskProfile(customer, riskQuestions),
            DisabilityService.riskProfile(customer, riskQuestions),
            HomeService.riskProfile(customer, riskQuestions),
            LifeService.riskProfile(customer, riskQuestions)
        )
}