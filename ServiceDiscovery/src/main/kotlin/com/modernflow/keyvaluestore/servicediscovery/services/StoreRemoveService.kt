package com.modernflow.keyvaluestore.servicediscovery.services

import com.modernflow.keyvaluestore.servicediscovery.clients.ProxyClient
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class StoreRemoveService(
    private val proxyClient: ProxyClient,
    private val redisTemplate: StringRedisTemplate,
) {
    fun removeStoreServer(serverAddress: String) {
        logger.info { "$serverAddress 스토어 서버 제거 요청" }

        val (ip, port) = serverAddress.split(":")
        proxyClient.delete(ip, port)
        redisTemplate.delete(serverAddress)
    }
}
