package com.modernflow.keyvaluestore.proxy.controllers

import com.modernflow.keyvaluestore.clients.ServiceDiscoveryClient
import com.modernflow.keyvaluestore.proxy.store.ConsistenceHashMap
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class InitController(
    private val consistenceHashMap: ConsistenceHashMap,
    private val serviceDiscoveryClient: ServiceDiscoveryClient,
) {
    @PostMapping(
        "/proxy/init-consistence-hash-map",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun init(): Boolean {
        val storeAddresses = serviceDiscoveryClient.getStoreAddress()
        return consistenceHashMap.createCirCle(storeAddresses)
    }
}
