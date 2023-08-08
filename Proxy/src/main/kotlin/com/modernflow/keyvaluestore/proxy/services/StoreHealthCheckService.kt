package com.modernflow.keyvaluestore.proxy.services

import com.modernflow.keyvaluestore.clients.ServiceDiscoveryClient
import com.modernflow.keyvaluestore.dtos.PhysicalNodeAddressDto
import com.modernflow.keyvaluestore.proxy.store.ConsistenceHashMap
import feign.FeignException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class StoreHealthCheckService(
    private val consistenceHashMap: ConsistenceHashMap,
    private val serviceDiscoveryClient: ServiceDiscoveryClient,
) {
    fun isStoreHealth(key: String): Pair<Boolean, PhysicalNodeAddressDto> {
        val (_, hash, physicalNode) = consistenceHashMap.getVirtualNode(key)
        val (ip, port) = physicalNode
        return try {
            val isStoreHealth = serviceDiscoveryClient.isHealthAddress(ip, port)
            Pair(isStoreHealth, physicalNode)
        } catch (ex: FeignException.InternalServerError) {
            logger.error { "isStoreHealth - InternalServerError 발생 key: $key, physicalNode: $physicalNode, message: ${ex.message}" }
            return Pair(false, physicalNode)
        } catch (ex: java.net.UnknownHostException) {
            logger.error { "isStoreHealth - UnknownHostException 발생 key: $key, physicalNode: $physicalNode, message: ${ex.message}" }
            return Pair(false, physicalNode)
        } catch (ex: Exception) {
            logger.error { "isStoreHealth - Exception 발생 key: $key, physicalNode: $physicalNode, message: ${ex.message}" }
            return Pair(false, physicalNode)
        }
    }
}
