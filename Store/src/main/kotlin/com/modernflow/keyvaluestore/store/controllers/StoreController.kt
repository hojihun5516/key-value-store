package com.modernflow.keyvaluestore.store.controllers

import com.modernflow.keyvaluestore.dtos.KeyValueStoreDto
import com.modernflow.keyvaluestore.dtos.StoreUpsertRequestDto
import com.modernflow.keyvaluestore.store.services.DataDeleteService
import com.modernflow.keyvaluestore.store.services.DataGetService
import com.modernflow.keyvaluestore.store.services.DataUpsertService
import org.springframework.http.MediaType
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
    @PutMapping(
        "/store/{key}",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun upsert(
        @PathVariable key: Long,
        @RequestBody storeUpsertRequestDto: StoreUpsertRequestDto,
    ): Boolean {
        return dataUpsertService.upsert(
            keyValueStoreDto = KeyValueStoreDto(
                key = key,
                value = storeUpsertRequestDto.value
            )
        )
    }

    @GetMapping(
        "/store/{key}",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun get(
        @PathVariable key: Long,
    ): Any? {
        return dataGetService.get(key)
    }

    @DeleteMapping(
        "/store/{key}",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun delete(
        @PathVariable key: Long,
    ): Boolean {
        return dataDeleteService.delete(key)
    }
}