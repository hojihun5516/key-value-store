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
    private val storeHealthCheckService: StoreHealthCheckService,
) {
    fun upsert(keyValueStoreRequestDto: KeyValueStoreRequestDto): Boolean {
        val (key, value) = keyValueStoreRequestDto
        val hashedKey = consistenceHashMap.hashKey(key)
        val (isStoreHealth, physicalNode) = storeHealthCheckService.isStoreHealth(key)
        val storeClient = physicalAddressClientService.getStoreClient(physicalNode)
        logger.info { "upsert - key: $key, value: $value, target physicalNode: $physicalNode" }
        if (isStoreHealth) {
            storeClient.upsert(
                key = hashedKey,
                storeUpsertRequestDto = StoreUpsertRequestDto(value!!)
            )
            return true
        } else {
            throw Exception("target store is not health condition")
        }
    }
}
