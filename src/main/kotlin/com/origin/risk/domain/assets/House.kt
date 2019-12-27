package com.origin.risk.domain.assets

data class House(
    val ownershipStatus: OwnershipStatus
)

enum class OwnershipStatus {
    OWNED,
    MORTGAGED
}