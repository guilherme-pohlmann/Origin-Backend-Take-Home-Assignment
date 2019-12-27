package com.origin.risk.domain.insurance.rules

import com.origin.risk.domain.customer.Customer
import com.origin.risk.domain.engine.RiskProfile
import com.origin.risk.domain.engine.Rule

internal object HighAgeRule : Rule {
    override fun apply(customer: Customer, riskProfile: RiskProfile) =
        if(customer.age > 60)
            riskProfile.ineligible()
        else
            riskProfile
}