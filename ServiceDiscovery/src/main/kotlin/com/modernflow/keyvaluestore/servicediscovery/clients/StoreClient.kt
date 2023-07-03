package com.modernflow.keyvaluestore.servicediscovery.clients

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping

interface StoreClient {
    @GetMapping("/health-check")
    fun healthCheck(): Boolean
}

@FeignClient(
    name = "firstStoreClient",
    url = "localhost:5000",
)
interface FirstStoreClient : StoreClient

@FeignClient(
    name = "secondStoreClient",
    url = "localhost:5100",
)
interface SecondStoreClient : StoreClient

@FeignClient(
    name = "thirdStoreClient",
    url = "localhost:5200",
)
interface ThirdStoreClient : StoreClient
