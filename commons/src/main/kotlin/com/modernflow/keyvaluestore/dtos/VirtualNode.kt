package com.modernflow.keyvaluestore.dtos

data class VirtualNode(
    val id: String,
    val hash: Long,
    val physicalNode: PhysicalNodeAddressDto,
)
