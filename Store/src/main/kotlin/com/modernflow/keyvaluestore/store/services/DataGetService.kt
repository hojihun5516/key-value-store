package com.modernflow.keyvaluestore.store.services

import com.modernflow.keyvaluestore.store.storage.DataManager
import org.springframework.stereotype.Service

@Service
class DataGetService(
    private val dataManager: DataManager
) {
    fun get(key: Long): Any? {
        return dataManager.get(key = key)
    }
}