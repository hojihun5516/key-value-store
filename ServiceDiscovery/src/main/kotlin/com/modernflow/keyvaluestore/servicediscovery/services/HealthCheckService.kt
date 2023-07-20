package com.modernflow.keyvaluestore.servicediscovery.services

import com.modernflow.keyvaluestore.dtos.PhysicalNodeAddressDto
import com.modernflow.keyvaluestore.services.PhysicalAddressClientService
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.springframework.stereotype.Service

@Service
class HealthCheckService(
    private val physicalAddressClientService: PhysicalAddressClientService,
    private val proxyService: ProxyService,
) {

    @OptIn(DelicateCoroutinesApi::class)
    fun isHealth(physicalNodeAddressDto: PhysicalNodeAddressDto): Boolean {
        val storeClient = physicalAddressClientService.getStoreClient(physicalNodeAddressDto)
        val isHealth = storeClient.healthCheck()
        if (!isHealth){
            GlobalScope.launch {
                proxyService.removeStoreServer(physicalNodeAddressDto)
            }
        }
        return isHealth
    }
}
