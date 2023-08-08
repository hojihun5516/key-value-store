package com.modernflow.keyvaluestore.proxy.services

import com.modernflow.keyvaluestore.dtos.KeyValueStoreRequestDto
import com.modernflow.keyvaluestore.proxy.store.ConsistenceHashMap
import com.modernflow.keyvaluestore.services.PhysicalAddressClientService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

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
        logger.info { "get - key: $key, target physicalNode: $physicalNode" }

        if (isStoreHealth) {
            return KeyValueStoreRequestDto(
                key = key,
                value = storeClient.get(key = hashedKey)?.value
            )
        } else {
            throw Exception("target store is not health condition")
        }
    }
}
