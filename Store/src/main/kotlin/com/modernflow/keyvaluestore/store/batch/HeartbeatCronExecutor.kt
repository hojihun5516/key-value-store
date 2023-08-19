package com.modernflow.keyvaluestore.store.batch

import com.modernflow.keyvaluestore.store.services.HeartbeatUpdateService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class HeartbeatCronExecutor(
    private val heartbeatUpdateService: HeartbeatUpdateService
) {
    /**
     * 1초 마다 실행
     */
    @Scheduled(cron = "*/1 * * * * *")
    fun updateHeartbeat() {
        heartbeatUpdateService.updateHeartbeat()
    }
}
