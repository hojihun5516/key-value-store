package com.example.keyvaluestore.services

import com.example.keyvaluestore.store.ConsistenceHashMap
import org.springframework.stereotype.Service

@Service
class StoreDeleteService(
    private val consistenceHashMap: ConsistenceHashMap
) {
    fun delete(key: String) {
        val virtualNode = consistenceHashMap.getVirtualNode(key)
        // TODO
        //  해당하는 Physical server IP에 요청을 보낸다
    }
}