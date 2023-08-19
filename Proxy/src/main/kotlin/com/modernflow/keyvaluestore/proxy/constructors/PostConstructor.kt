package com.modernflow.keyvaluestore.proxy.constructors

import com.modernflow.keyvaluestore.address.PhysicalAddress
import com.modernflow.keyvaluestore.proxy.store.ConsistenceHashMap
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class PostConstructor(
    private val consistenceHashMap: ConsistenceHashMap
) {
    @PostConstruct
    fun init(): Boolean {
        logger.info { "created consistenceHashMap" }
        val storeAddresses = PhysicalAddress.availablePhysicalAddresses
        return consistenceHashMap.createCirCle(storeAddresses)
    }
}