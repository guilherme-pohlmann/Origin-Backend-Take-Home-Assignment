package com.origin.risk.domain.insurance

import com.origin.risk.domain.assets.OwnershipStatus
import com.origin.risk.domain.customer.Customer
import com.origin.risk.domain.insurance.auto.AutoService
import com.origin.risk.domain.insurance.disability.DisabilityService
import com.origin.risk.domain.insurance.home.HomeService
import com.origin.risk.domain.insurance.life.LifeService
import com.origin.risk.domain.insurance.rent.RentService

object RiskProfileService {
    fun riskProfile(customer: Customer, riskQuestions: Array<Int>) = sequence {
        yield(DisabilityService.riskProfile(customer, riskQuestions))
        yield(LifeService.riskProfile(customer, riskQuestions))
        AutoService.riskProfile(customer, riskQuestions).forEach {
            yield(it)
        }

        if(customer.house.exists { it.ownershipStatus == OwnershipStatus.RENTED })
            yield(RentService.riskProfile(customer, riskQuestions))
        else
            yield(HomeService.riskProfile(customer, riskQuestions))
    }
}