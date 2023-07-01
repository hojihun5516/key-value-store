package com.modernflow.keyvaluestore.store.services

import com.modernflow.keyvaluestore.store.storage.DataManager
import org.springframework.stereotype.Service

@Service
class DataDeleteService(
    private val dataManager: DataManager
) {
    fun delete(key: Long): Boolean {
        dataManager.delete(key = key)
        return true
    }
}