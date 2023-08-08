package com.modernflow.keyvaluestore.servicediscovery.controllers

import com.modernflow.keyvaluestore.dtos.PhysicalNodeAddressDto
import com.modernflow.keyvaluestore.servicediscovery.address.PhysicalAddress
import com.modernflow.keyvaluestore.servicediscovery.services.HealthCheckService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class StoreController(
    private val healthCheckService: HealthCheckService
) {
    @GetMapping(
        "/service-discovery/store-addresses",
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun listUpStorePhysicalAddress(): List<PhysicalNodeAddressDto> {
        return PhysicalAddress.availablePhysicalAddresses
    }

    @GetMapping(
        "/service-discovery/store-addresses/ip/{ip}/port/{port}",
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun isHealthAddress(
        @PathVariable ip: String,
        @PathVariable port: Int,
    ): Boolean {
        return healthCheckService.isHealth(PhysicalNodeAddressDto(ip, port))
    }
}
