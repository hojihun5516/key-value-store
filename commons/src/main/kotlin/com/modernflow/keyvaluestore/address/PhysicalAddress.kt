package com.modernflow.keyvaluestore.address

import com.modernflow.keyvaluestore.dtos.PhysicalNodeAddressDto

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