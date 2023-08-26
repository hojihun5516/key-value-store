package com.modernflow.keyvaluestore.store.controllers

import com.modernflow.keyvaluestore.dtos.HashedKeyValueStoreDto
import com.modernflow.keyvaluestore.dtos.StoreValueDto
import com.modernflow.keyvaluestore.store.services.DataDeleteService
import com.modernflow.keyvaluestore.store.services.DataGetService
import com.modernflow.keyvaluestore.store.services.DataUpsertService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class StoreController(
    private val dataUpsertService: DataUpsertService,
    private val dataDeleteService: DataDeleteService,
    private val dataGetService: DataGetService,
) {
    @PutMapping("/store/{key}")
    fun upsert(@PathVariable key: Long, @RequestBody storeValueDto: StoreValueDto): Boolean {
        return dataUpsertService.upsert(
            hashedKeyValueStoreDto = HashedKeyValueStoreDto(
                key = key,
                value = storeValueDto.value
            )
        )
    }

    @GetMapping("/store/{key}")
    fun get(@PathVariable key: Long): StoreValueDto? {
        return dataGetService.get(key)?.let { StoreValueDto(value = it) }
    }

    @DeleteMapping("/store/{key}")
    fun delete(@PathVariable key: Long): Boolean {
        return dataDeleteService.delete(key)
    }
}