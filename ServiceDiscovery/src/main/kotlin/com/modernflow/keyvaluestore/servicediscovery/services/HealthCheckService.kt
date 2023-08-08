package com.modernflow.keyvaluestore.servicediscovery.services

import com.modernflow.keyvaluestore.dtos.PhysicalNodeAddressDto
import com.modernflow.keyvaluestore.servicediscovery.address.PhysicalAddress
import com.modernflow.keyvaluestore.services.PhysicalAddressClientService
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class HealthCheckService(
    private val physicalAddressClientService: PhysicalAddressClientService,
    private val proxyService: ProxyService,
) {

    @OptIn(DelicateCoroutinesApi::class)
    fun isHealth(physicalNodeAddressDto: PhysicalNodeAddressDto): Boolean {
        val storeClient = physicalAddressClientService.getStoreClient(physicalNodeAddressDto)
        return try {
            val isHealth = storeClient.healthCheck()
            isHealth
        } catch (ex: Exception) {
            logger.error { "client no response - physicalNodeAddressDto: $physicalNodeAddressDto" }
            PhysicalAddress.removePhysicalNode(
                ip = physicalNodeAddressDto.ip,
                port = physicalNodeAddressDto.port,
            )
            GlobalScope.launch {
                logger.info { "requesting proxy server to remove store - physicalNodeAddressDto: $physicalNodeAddressDto" }
                proxyService.removeStoreServer(physicalNodeAddressDto)
            }
            throw RuntimeException("exception message ${ex.message}")
        }
    }
}
