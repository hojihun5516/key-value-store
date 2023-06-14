package com.example.keyvaluestore.services

import com.example.keyvaluestore.dtos.KeyValueStoreDto
import com.example.keyvaluestore.store.ConsistenceHashMap
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