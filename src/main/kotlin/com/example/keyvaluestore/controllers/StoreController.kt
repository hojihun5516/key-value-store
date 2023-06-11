package com.example.keyvaluestore.controllers

import com.example.keyvaluestore.dtos.KeyValueStoreDto
import com.example.keyvaluestore.dtos.StoreUpsertRequestDto
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class StoreController(
) {
    @PutMapping(
        "/store/{key}",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun upsert(
        @PathVariable key: String,
        @RequestBody storeUpsertRequestDto: StoreUpsertRequestDto,
    ): KeyValueStoreDto {
        return TODO("Provide the return value")
    }

    @GetMapping(
        "/store/{key}",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun get(
        @PathVariable key: String,
    ): KeyValueStoreDto {
        return TODO("Provide the return value")
    }


    @DeleteMapping(
        "/store/{key}",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun delete(
        @PathVariable key: String,
    ): KeyValueStoreDto {
        return TODO("Provide the return value")
    }
}