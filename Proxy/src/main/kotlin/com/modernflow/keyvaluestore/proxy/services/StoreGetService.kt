package com.modernflow.keyvaluestore.proxy.services

import com.modernflow.keyvaluestore.dtos.KeyValueStoreRequestDto
import com.modernflow.keyvaluestore.proxy.store.ConsistenceHashMap
import com.modernflow.keyvaluestore.services.PhysicalAddressClientService
import org.springframework.stereotype.Service

@Service
class StoreGetService(
    private val consistenceHashMap: ConsistenceHashMap,
    private val physicalAddressClientService: PhysicalAddressClientService,
    private val storeHealthCheckService: StoreHealthCheckService,
) {
    fun get(key: String): KeyValueStoreRequestDto {
        val hashedKey = consistenceHashMap.hashKey(key)
        val (isStoreHealth, physicalNode) = storeHealthCheckService.isStoreHealth(key)
        val storeClient = physicalAddressClientService.getStoreClient(physicalNode)

        if (isStoreHealth) {
            return KeyValueStoreRequestDto(
                key = key,
                value = storeClient.get(key = hashedKey)
            )
        } else {
            throw Exception("target store is not health condition")
        }
    }
}
