package com.modernflow.keyvaluestore.proxy.controllers

import com.modernflow.keyvaluestore.dtos.PhysicalNodeAddressDto
import com.modernflow.keyvaluestore.proxy.store.ConsistenceHashMap
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

private val logger = KotlinLogging.logger {}

@RestController
class ConsistenceHashMapController(
    private val consistenceHashMap: ConsistenceHashMap,
) {
    @DeleteMapping("/proxy/consistence-hash-map/ip/{ip}/port/{port}")
    fun delete(
        @PathVariable ip: String,
        @PathVariable port: Int,
    ) {
        logger.info { "remove physical node request - $ip, $port" }
        consistenceHashMap.removePhysicalNode(PhysicalNodeAddressDto(ip = ip, port = port))
    }
}
