package com.modernflow.keyvaluestore.store.services

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class HeartbeatService(
    @Value("\${SERVER_NAME}")
    private val serverName: String,
    @Value("\${SERVER_PORT}")
    private val port: Int,
    private val redisTemplate: StringRedisTemplate,
) {

    @Scheduled(fixedRate = 5000)
    fun updateHeartbeat() {
        val serverIp = "$serverName:$port"
        val currentHeartbeatCount = redisTemplate.opsForValue().increment(serverIp) ?: 0
        logger.info { "Updated heartbeat for server $serverIp: $currentHeartbeatCount" }
    }
}
