package com.modernflow.keyvaluestore.proxy.services

import com.modernflow.keyvaluestore.clients.ServiceDiscoveryClient
import com.modernflow.keyvaluestore.dtos.PhysicalNodeAddressDto
import com.modernflow.keyvaluestore.proxy.store.ConsistenceHashMap
import org.springframework.stereotype.Service

@Service
class StoreHealthCheckService(
    private val consistenceHashMap: ConsistenceHashMap,
    private val serviceDiscoveryClient: ServiceDiscoveryClient,
) {
    fun isStoreHealth(key: String): Pair<Boolean, PhysicalNodeAddressDto> {
        val (_, hash, physicalNode) = consistenceHashMap.getVirtualNode(key)
        val (ip, port) = physicalNode
        val isStoreHealth = serviceDiscoveryClient.isHealthAddress(ip, port)
        return Pair(isStoreHealth, physicalNode)
    }
}
