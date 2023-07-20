package com.modernflow.keyvaluestore.proxy.services

import com.modernflow.keyvaluestore.proxy.store.ConsistenceHashMap
import com.modernflow.keyvaluestore.services.PhysicalAddressClientService
import org.springframework.stereotype.Service

@Service
class StoreDeleteService(
    private val consistenceHashMap: ConsistenceHashMap,
    private val storeHealthCheckService: StoreHealthCheckService,
    private val physicalAddressClientService: PhysicalAddressClientService,
) {
    fun delete(key: String): Boolean {
        val hashedKey = consistenceHashMap.hashKey(key)
        val (isStoreHealth, physicalNode) = storeHealthCheckService.isStoreHealth(key)
        val storeClient = physicalAddressClientService.getStoreClient(physicalNode)

        if (isStoreHealth) {
            return storeClient.delete(key = hashedKey)
        } else {
            throw Exception("target store is not health condition")
        }
    }
}