package com.origin.risk.domain.engine

import com.origin.risk.domain.customer.Customer

internal interface Rule {
    fun apply(customer: Customer, riskProfile: RiskProfile): RiskProfile
}