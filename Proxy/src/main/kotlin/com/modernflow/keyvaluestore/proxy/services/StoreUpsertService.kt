package com.modernflow.keyvaluestore.proxy.services

import com.modernflow.keyvaluestore.dtos.KeyValueStoreRequestDto
import com.modernflow.keyvaluestore.dtos.StoreUpsertRequestDto
import com.modernflow.keyvaluestore.proxy.store.ConsistenceHashMap
import com.modernflow.keyvaluestore.services.PhysicalAddressClientService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class StoreUpsertService(
    private val consistenceHashMap: ConsistenceHashMap,
    private val physicalAddressClientService: PhysicalAddressClientService,
) {
    fun upsert(keyValueStoreRequestDto: KeyValueStoreRequestDto): Boolean {
        val (key, value) = keyValueStoreRequestDto
        val hashedKey = consistenceHashMap.hashKey(key)
        val (_, hash, physicalNode) = consistenceHashMap.getVirtualNode(key)
        logger.info { "upsert - key: $key, value: $value, target physicalNode: $physicalNode" }

        val storeClient = physicalAddressClientService.getStoreClient(physicalNode)
        return try {
            storeClient.upsert(
                key = hashedKey,
                storeUpsertRequestDto = StoreUpsertRequestDto(value!!)
            )
        } catch (e: Exception) {
            logger.error { "upsert error message: $e" }
            throw e
        }
    }
}
