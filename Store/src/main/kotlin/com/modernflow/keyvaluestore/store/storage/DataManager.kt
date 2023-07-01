package com.modernflow.keyvaluestore.store.storage

interface DataManager {
    fun upsert(key: Long, value: Any)

    fun get(key: Long): Any?

    fun delete(key: Long)
}
