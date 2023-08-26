package com.modernflow.keyvaluestore.proxy.services

import com.modernflow.keyvaluestore.dtos.KeyValueStoreDto
import com.modernflow.keyvaluestore.proxy.store.ConsistenceHashMap
import com.modernflow.keyvaluestore.services.PhysicalAddressClientService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class StoreGetService(
    private val consistenceHashMap: ConsistenceHashMap,
    private val physicalAddressClientService: PhysicalAddressClientService,
) {
    fun get(key: String): KeyValueStoreDto {
        val hashedKey = consistenceHashMap.hashKey(key)
        val physicalNode = consistenceHashMap.getPhysicalNode(key)

        val storeClient = physicalAddressClientService.getStoreClient(physicalNode)
        logger.info { "get - key: $key, target physicalNode: $physicalNode" }

        return try {
            KeyValueStoreDto(
                key = key,
                value = storeClient.get(key = hashedKey)?.value
            )
        } catch (e: Exception) {
            logger.error { "get error message: $e" }
            throw e
        }
    }
}
