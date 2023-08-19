package com.modernflow.keyvaluestore.store.services

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class HeartbeatUpdateService(
    @Value("\${SERVER_NAME}")
    private val serverName: String,
    @Value("\${SERVER_PORT}")
    private val port: Int,
    private val redisTemplate: StringRedisTemplate,
) {

    fun updateHeartbeat(): Long {
        val serverIp = "$serverName:$port"
        val heartbeatCount = redisTemplate.opsForValue().increment(serverIp) ?: 0
        logger.info { "Updated heartbeat for server $serverIp: $heartbeatCount" }
        return heartbeatCount
    }
}
