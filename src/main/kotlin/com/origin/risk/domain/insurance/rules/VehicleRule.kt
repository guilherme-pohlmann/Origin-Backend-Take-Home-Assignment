package com.origin.risk.domain.insurance.rules

import com.origin.risk.domain.customer.Customer
import com.origin.risk.domain.engine.RiskProfile
import com.origin.risk.domain.engine.Rule

internal object VehicleRule : Rule {
    override fun apply(customer: Customer, riskProfile: RiskProfile) =
        if(customer.vehicle.isEmpty())
            riskProfile.ineligible()
        else
            riskProfile
}