package com.origin.risk.domain.insurance.rules

import com.origin.risk.domain.customer.Customer
import com.origin.risk.domain.customer.MaritalStatus
import com.origin.risk.domain.engine.RiskProfile
import com.origin.risk.domain.engine.Rule

internal object DisabilityInsuranceMarriedRule : Rule {
    override fun apply(customer: Customer, riskProfile: RiskProfile) =
        if(customer.maritalStatus == MaritalStatus.MARRIED)
            riskProfile.deduct(1)
        else
            riskProfile
}