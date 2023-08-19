package com.modernflow.keyvaluestore.address

import com.modernflow.keyvaluestore.dtos.PhysicalNodeAddressDto

object PhysicalAddress {
    val FIRST_PHYSICAL_NODE = PhysicalNodeAddressDto(
        ip = "store-service-1",
        port = 5000,
    )

    val SECOND_PHYSICAL_NODE = PhysicalNodeAddressDto(
        ip = "store-service-2",
        port = 5100,
    )

    val THIRD_PHYSICAL_NODE = PhysicalNodeAddressDto(
        ip = "store-service-3",
        port = 5200,
    )

    val availablePhysicalAddresses = mutableListOf(
        FIRST_PHYSICAL_NODE,
        SECOND_PHYSICAL_NODE,
        THIRD_PHYSICAL_NODE,
    )
}