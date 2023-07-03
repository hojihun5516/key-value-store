package com.modernflow.keyvaluestore.servicediscovery.services

import com.modernflow.keyvaluestore.servicediscovery.addresses.PhysicalAddress.FIRST_PHYSICAL_NODE
import com.modernflow.keyvaluestore.servicediscovery.addresses.PhysicalAddress.SECOND_PHYSICAL_NODE
import com.modernflow.keyvaluestore.servicediscovery.addresses.PhysicalAddress.THIRD_PHYSICAL_NODE
import com.modernflow.keyvaluestore.servicediscovery.clients.FirstStoreClient
import com.modernflow.keyvaluestore.servicediscovery.clients.SecondStoreClient
import com.modernflow.keyvaluestore.servicediscovery.clients.StoreClient
import com.modernflow.keyvaluestore.servicediscovery.clients.ThirdStoreClient
import com.modernflow.keyvaluestore.servicediscovery.dtos.PhysicalNodeAddressDto
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
