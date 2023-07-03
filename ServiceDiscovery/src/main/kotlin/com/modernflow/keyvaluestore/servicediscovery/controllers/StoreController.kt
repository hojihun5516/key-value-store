package com.modernflow.keyvaluestore.servicediscovery.controllers

import com.modernflow.keyvaluestore.servicediscovery.addresses.PhysicalAddress
import com.modernflow.keyvaluestore.servicediscovery.dtos.PhysicalNodeAddressDto
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class StoreController {
    @GetMapping(
        "/store-addresses",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun listUpStorePhysicalAddress(): List<PhysicalNodeAddressDto> {
        return PhysicalAddress.getStorePhysicalNodes()
    }
}
