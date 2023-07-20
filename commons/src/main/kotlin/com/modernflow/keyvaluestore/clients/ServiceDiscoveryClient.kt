package com.modernflow.keyvaluestore.clients

import com.modernflow.keyvaluestore.dtos.PhysicalNodeAddressDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(
    name = "serviceDiscoveryClient",
    url = "localhost:8000",
)
interface ServiceDiscoveryClient {
    @GetMapping("/service-discovery/store-addresses")
    fun getStoreAddress(): List<PhysicalNodeAddressDto>

    @GetMapping(
        "/service-discovery/store-addresses/ip/{ip}/port/{port}",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun isHealthAddress(
        @PathVariable ip: String,
        @PathVariable port: Int,
    ): Boolean
}
