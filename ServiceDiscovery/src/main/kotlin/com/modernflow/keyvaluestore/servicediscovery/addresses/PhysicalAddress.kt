package com.modernflow.keyvaluestore.servicediscovery.addresses

import com.modernflow.keyvaluestore.servicediscovery.dtos.PhysicalNodeAddressDto

object PhysicalAddress {
    val FIRST_PHYSICAL_NODE = PhysicalNodeAddressDto(
        ip = "localhost",
        port = 5000,
    )

    val SECOND_PHYSICAL_NODE = PhysicalNodeAddressDto(
        ip = "localhost",
        port = 5100,
    )

    val THIRD_PHYSICAL_NODE = PhysicalNodeAddressDto(
        ip = "localhost",
        port = 5200,
    )

    fun getStorePhysicalNodes(): List<PhysicalNodeAddressDto> {
        return listOf(
            FIRST_PHYSICAL_NODE,
            SECOND_PHYSICAL_NODE,
            THIRD_PHYSICAL_NODE,
        )
    }
}