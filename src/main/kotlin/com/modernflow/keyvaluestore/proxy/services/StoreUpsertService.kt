package com.modernflow.keyvaluestore.proxy.services

import com.modernflow.keyvaluestore.proxy.dtos.KeyValueStoreDto
import com.modernflow.keyvaluestore.proxy.store.ConsistenceHashMap
import org.springframework.stereotype.Service

@Service
class StoreUpsertService(
    private val consistenceHashMap: ConsistenceHashMap
) {
    fun upsert(keyValueStoreDto: KeyValueStoreDto): KeyValueStoreDto {
        val virtualNode = consistenceHashMap.getVirtualNode(keyValueStoreDto.key)
        // TODO
        //  해당하는 Physical server IP에 요청을 보낸다
        return keyValueStoreDto
    }
}