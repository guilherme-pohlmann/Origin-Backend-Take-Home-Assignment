package com.origin.risk.domain.insurance

import com.origin.risk.domain.customer.Customer
import com.origin.risk.domain.insurance.auto.AutoService
import com.origin.risk.domain.insurance.disability.DisabilityService
import com.origin.risk.domain.insurance.home.HomeService
import com.origin.risk.domain.insurance.life.LifeService

object RiskProfileService {
    fun riskProfile(customer: Customer, riskQuestions: Array<Int>) =
        listOf(
            DisabilityService.riskProfile(customer, riskQuestions),
            HomeService.riskProfile(customer, riskQuestions),
            LifeService.riskProfile(customer, riskQuestions)
        ) + AutoService.riskProfile(customer, riskQuestions).toList()
}