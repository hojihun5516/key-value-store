package com.modernflow.keyvaluestore.store.storage

import org.springframework.stereotype.Component
import java.util.concurrent.ConcurrentHashMap

@Component
class InMemoryDataManager : DataManager {
    private val dataMap: MutableMap<Long, Any> = ConcurrentHashMap<Long, Any>()

    override fun upsert(key: Long, value: Any): Boolean {
        dataMap[key] = value
        return true
    }

    override fun get(key: Long): Any? {
        return this.dataMap[key]
    }

    override fun delete(key: Long): Boolean {
        dataMap.remove(key)
        return true
    }
}
