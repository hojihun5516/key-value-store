package com.modernflow.keyvaluestore.proxy.services

import com.modernflow.keyvaluestore.proxy.dtos.KeyValueStoreDto
import com.modernflow.keyvaluestore.proxy.store.ConsistenceHashMap
import org.springframework.stereotype.Service

@Service
class StoreGetService(
    private val consistenceHashMap: ConsistenceHashMap
) {
    fun get(key: String): KeyValueStoreDto {
        val virtualNode = consistenceHashMap.getVirtualNode(key)
        // TODO
        //  해당하는 Physical server IP에 요청을 보낸다
        return KeyValueStoreDto(key = key, value = "TEMP")
    }
}