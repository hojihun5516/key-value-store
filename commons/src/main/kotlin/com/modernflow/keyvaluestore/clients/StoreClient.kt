package com.modernflow.keyvaluestore.clients

import com.modernflow.keyvaluestore.dtos.StoreValueDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody

interface StoreClient {
    @PutMapping(
        "/store/{key}",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun upsert(
        @PathVariable key: Long,
        @RequestBody storeValueDto: StoreValueDto,
    ): Boolean

    @GetMapping("/store/{key}")
    fun get(@PathVariable key: Long): StoreValueDto?

    @DeleteMapping("/store/{key}")
    fun delete(@PathVariable key: Long): Boolean

    @GetMapping("/health-check")
    fun healthCheck(): Boolean
}

@FeignClient(
    name = "firstStoreClient",
    url = "store-service-1:5000",
)
interface FirstStoreClient : StoreClient

@FeignClient(
    name = "secondStoreClient",
    url = "store-service-2:5100",
)
interface SecondStoreClient : StoreClient

@FeignClient(
    name = "thirdStoreClient",
    url = "store-service-3:5200",
)
interface ThirdStoreClient : StoreClient
