package com.modernflow.keyvaluestore.servicediscovery.services

import com.modernflow.keyvaluestore.servicediscovery.clients.ProxyClient
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.redis.core.StringRedisTemplate

@ExtendWith(MockKExtension::class)
class StoreRemoveServiceTest(
    @MockK private val proxyClient: ProxyClient,
    @MockK private val redisTemplate: StringRedisTemplate,
) {
    @InjectMockKs
    private lateinit var sut: StoreRemoveService

    @Test
    fun `sut remove store server when server address is given`() {
        // Arrange
        val serverAddress = "service-store-1:5000"
        justRun { proxyClient.delete("service-store-1", "5000") }
        every { redisTemplate.delete(serverAddress) } returns true

        // Act
        sut.removeStoreServer(serverAddress)

        // Assert
        verify { proxyClient.delete("service-store-1", "5000") }
        verify { redisTemplate.delete(serverAddress) }
    }
}