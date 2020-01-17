package com.origin.risk.domain.assets

data class House(
    val ownershipStatus: OwnershipStatus,
    val mortgaged: Boolean
)

enum class OwnershipStatus {
    OWNED,
    RENTED
}