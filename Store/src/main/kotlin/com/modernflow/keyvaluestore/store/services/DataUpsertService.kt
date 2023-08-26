package com.modernflow.keyvaluestore.store.services

import com.modernflow.keyvaluestore.dtos.HashedKeyValueStoreDto
import com.modernflow.keyvaluestore.store.storage.DataManager
import org.springframework.stereotype.Service

@Service
class DataUpsertService(
    private val dataManager: DataManager,
) {
    fun upsert(hashedKeyValueStoreDto: HashedKeyValueStoreDto): Boolean {
        dataManager.upsert(key = hashedKeyValueStoreDto.key, value = hashedKeyValueStoreDto.value)
        return true
    }
}
