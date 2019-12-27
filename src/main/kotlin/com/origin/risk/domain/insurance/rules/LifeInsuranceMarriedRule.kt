package com.origin.risk.domain.insurance.rules

import com.origin.risk.domain.customer.Customer
import com.origin.risk.domain.customer.MaritalStatus
import com.origin.risk.domain.engine.RiskProfile
import com.origin.risk.domain.engine.Rule

internal object LifeInsuranceMarriedRule : Rule {
    override fun apply(customer: Customer, riskProfile: RiskProfile) =
        if(customer.maritalStatus == MaritalStatus.MARRIED)
            riskProfile.add(1)
        else
            riskProfile
}