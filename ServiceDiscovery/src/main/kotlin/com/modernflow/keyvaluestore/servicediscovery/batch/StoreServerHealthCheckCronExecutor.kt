package com.modernflow.keyvaluestore.servicediscovery.batch

import com.modernflow.keyvaluestore.servicediscovery.services.StoreRemoveService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger {}

@Component
class StoreServerHealthCheckCronExecutor(
    private val redisTemplate: StringRedisTemplate,
    private val storeRemoveService: StoreRemoveService,
) {
    private val serverHeartbeats = mutableMapOf<String, Long?>()

    /**
     * 5초 마다 실행
     */
    @Scheduled(cron = "*/5 * * * * *")
    fun checkHeartbeats() {
        logger.info{"Check Store Server HeartBeat"}
        val serverAddresses = redisTemplate.keys("*")
        serverAddresses.forEach { serverAddress ->
            val heartbeat = redisTemplate.opsForValue().get(serverAddress)?.toLong()

            if (serverHeartbeats[serverAddress] == heartbeat) {
                logger.info { "$serverAddress was dead" }
                storeRemoveService.removeStoreServer(serverAddress)
            } else {
                serverHeartbeats[serverAddress] = heartbeat
            }
        }
    }
}
