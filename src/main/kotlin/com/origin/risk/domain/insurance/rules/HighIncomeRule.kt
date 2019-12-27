package com.origin.risk.domain.insurance.rules

import com.origin.risk.domain.customer.Customer
import com.origin.risk.domain.engine.RiskProfile
import com.origin.risk.domain.engine.Rule

internal object HighIncomeRule : Rule {
    override fun apply(customer: Customer, riskProfile: RiskProfile)=
        if (customer.income > 200000)
            riskProfile.deduct(1)
        else
            riskProfile
}