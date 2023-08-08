package com.modernflow.keyvaluestore.proxy.controllers

import com.modernflow.keyvaluestore.dtos.KeyValueStoreRequestDto
import com.modernflow.keyvaluestore.dtos.StoreUpsertRequestDto
import com.modernflow.keyvaluestore.proxy.services.StoreDeleteService
import com.modernflow.keyvaluestore.proxy.services.StoreGetService
import com.modernflow.keyvaluestore.proxy.services.StoreUpsertService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class StoreController(
    private val storeUpsertService: StoreUpsertService,
    private val storeGetService: StoreGetService,
    private val storeDeleteService: StoreDeleteService,
) {
    @PutMapping(
        "/proxy/store/{key}",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun upsert(
        @PathVariable key: String,
        @RequestBody storeUpsertRequestDto: StoreUpsertRequestDto,
    ): Boolean {
        return storeUpsertService.upsert(
            keyValueStoreRequestDto = KeyValueStoreRequestDto(
                key = key,
                value = storeUpsertRequestDto.value
            )
        )
    }

    @GetMapping(
        "/proxy/store/{key}",
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun get(@PathVariable key: String): KeyValueStoreRequestDto {
        return storeGetService.get(key)
    }

    @DeleteMapping("/proxy/store/{key}")
    fun delete(@PathVariable key: String): Boolean {
        return storeDeleteService.delete(key)
    }
}