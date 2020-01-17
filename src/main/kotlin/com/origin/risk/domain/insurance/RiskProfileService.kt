package com.origin.risk.domain.insurance

import com.origin.risk.domain.customer.Customer
import com.origin.risk.domain.insurance.auto.AutoService

object RiskProfileService {
    fun riskProfile(customer: Customer, riskQuestions: Array<Int>) =
        listOf(
            DisabilityService.riskProfile(customer, riskQuestions),
            HomeService.riskProfile(customer, riskQuestions),
            LifeService.riskProfile(customer, riskQuestions)
        ) + AutoService.riskProfile(customer, riskQuestions).toList()
}