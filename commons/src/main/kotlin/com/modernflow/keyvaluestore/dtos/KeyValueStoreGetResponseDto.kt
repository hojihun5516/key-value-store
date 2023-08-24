package com.modernflow.keyvaluestore.dtos

data class KeyValueStoreGetResponseDto(
    val key: String,
    val value: Any?,
)
