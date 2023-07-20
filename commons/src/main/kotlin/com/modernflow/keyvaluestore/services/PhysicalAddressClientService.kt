package com.modernflow.keyvaluestore.services

import com.modernflow.keyvaluestore.address.PhysicalAddress.FIRST_PHYSICAL_NODE
import com.modernflow.keyvaluestore.address.PhysicalAddress.SECOND_PHYSICAL_NODE
import com.modernflow.keyvaluestore.address.PhysicalAddress.THIRD_PHYSICAL_NODE
import com.modernflow.keyvaluestore.clients.FirstStoreClient
import com.modernflow.keyvaluestore.clients.SecondStoreClient
import com.modernflow.keyvaluestore.clients.StoreClient
import com.modernflow.keyvaluestore.clients.ThirdStoreClient
import com.modernflow.keyvaluestore.dtos.PhysicalNodeAddressDto
import org.springframework.stereotype.Service

@Service
class PhysicalAddressClientService(
    private val firstStoreClient: FirstStoreClient,
    private val secondStoreClient: SecondStoreClient,
    private val thirdStoreClient: ThirdStoreClient,
) {
    fun getStoreClient(physicalNodeAddressDto: PhysicalNodeAddressDto): StoreClient {
        return when (physicalNodeAddressDto) {
            FIRST_PHYSICAL_NODE -> firstStoreClient
            SECOND_PHYSICAL_NODE -> secondStoreClient
            THIRD_PHYSICAL_NODE -> thirdStoreClient
            else -> throw Exception("not found client")
        }
    }
}
