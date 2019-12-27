package com.origin.risk.domain.insurance.rules

import com.origin.risk.domain.customer.Customer
import com.origin.risk.domain.engine.RiskProfile
import com.origin.risk.domain.engine.Rule

internal object LowAgeRule : Rule {
    override fun apply(customer: Customer, riskProfile: RiskProfile) =
        when (customer.age) {
             in 0..29 -> riskProfile.deduct(2)
             in 30..40 -> riskProfile.deduct(1)
             else -> riskProfile
        }
}