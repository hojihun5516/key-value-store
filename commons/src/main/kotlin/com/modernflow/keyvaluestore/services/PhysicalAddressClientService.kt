package com.modernflow.keyvaluestore.services

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
            PhysicalNodeAddressDto(
                ip = "store-service-1",
                port = 5000,
            ) -> firstStoreClient

            PhysicalNodeAddressDto(
                ip = "store-service-2",
                port = 5100,
            ) -> secondStoreClient

            PhysicalNodeAddressDto(
                ip = "store-service-3",
                port = 5200,
            ) -> thirdStoreClient

            else -> throw Exception("not found client")
        }
    }
}
