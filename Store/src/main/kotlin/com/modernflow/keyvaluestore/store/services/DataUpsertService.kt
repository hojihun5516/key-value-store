package com.modernflow.keyvaluestore.store.services

import com.modernflow.keyvaluestore.store.dtos.KeyValueStoreDto
import com.modernflow.keyvaluestore.store.storage.DataManager
import org.springframework.stereotype.Service

@Service
class DataUpsertService(
    private val dataManager: DataManager
) {
    fun upsert(keyValueStoreDto: KeyValueStoreDto): Boolean {
        dataManager.upsert(key = keyValueStoreDto.key, value = keyValueStoreDto.value)
        return true
    }
}
