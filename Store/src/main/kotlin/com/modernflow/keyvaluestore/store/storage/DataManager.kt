package com.modernflow.keyvaluestore.store.storage

interface DataManager {
    fun upsert(key: Long, value: Any): Boolean

    fun get(key: Long): Any?

    fun delete(key: Long): Boolean
}
