package com.modernflow.keyvaluestore.proxy.services

import com.modernflow.keyvaluestore.proxy.store.ConsistenceHashMap
import com.modernflow.keyvaluestore.services.PhysicalAddressClientService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

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
        logger.info { "delete - key: $key, target physicalNode: $physicalNode" }

        if (isStoreHealth) {
            return storeClient.delete(key = hashedKey)
        } else {
            throw Exception("target store is not health condition")
        }
    }
}