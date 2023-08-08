package com.modernflow.keyvaluestore.proxy.controllers

import com.modernflow.keyvaluestore.clients.ServiceDiscoveryClient
import com.modernflow.keyvaluestore.dtos.PhysicalNodeAddressDto
import com.modernflow.keyvaluestore.proxy.store.ConsistenceHashMap
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ConsistenceHashMapController(
    private val consistenceHashMap: ConsistenceHashMap,
    private val serviceDiscoveryClient: ServiceDiscoveryClient,
) {
    @PostMapping(
        "/proxy/consistence-hash-map/init",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun init(): Boolean {
        val storeAddresses = serviceDiscoveryClient.getStoreAddress()
        return consistenceHashMap.createCirCle(storeAddresses)
    }

    @DeleteMapping("/proxy/consistence-hash-map/ip/{ip}/port/{port}")
    fun delete(
        @PathVariable ip: String,
        @PathVariable port: Int,
    ) {
        consistenceHashMap.removePhysicalNode(
            PhysicalNodeAddressDto(ip = ip, port = port)
        )
    }
}
