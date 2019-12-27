package com.origin.risk.domain.insurance.rules

import com.origin.risk.domain.customer.Customer
import com.origin.risk.domain.assets.OwnershipStatus
import com.origin.risk.domain.engine.RiskProfile
import com.origin.risk.domain.engine.Rule

internal object MortgagedHouseRule : Rule {
    override fun apply(customer: Customer, riskProfile: RiskProfile) =
        if (customer.house.exists { it.ownershipStatus == OwnershipStatus.MORTGAGED })
            riskProfile.add(1)
        else
            riskProfile
}