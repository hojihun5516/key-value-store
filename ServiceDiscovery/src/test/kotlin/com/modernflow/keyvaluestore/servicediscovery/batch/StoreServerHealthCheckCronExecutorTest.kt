package com.modernflow.keyvaluestore.servicediscovery.batch

import com.modernflow.keyvaluestore.servicediscovery.services.StoreRemoveService
import io.mockk.Called
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.ValueOperations

@ExtendWith(MockKExtension::class)
class StoreServerHealthCheckCronExecutorTest(
    @MockK private val redisTemplate: StringRedisTemplate,
    @MockK private val storeRemoveService: StoreRemoveService,
) {
    @InjectMockKs
    private lateinit var sut: StoreServerHealthCheckCronExecutor

    @Test
    fun `sut should remove dead server`() {
        // Arrange
        val serverAddress = "serverAddress"
        val valueOps = mockk<ValueOperations<String, String>>()

        every { redisTemplate.keys(any()) } returns setOf(serverAddress)
        every { redisTemplate.opsForValue() } returns valueOps
        every { valueOps.get(serverAddress) } returns "111"
        justRun { storeRemoveService.removeStoreServer(serverAddress) }

        // Act
        sut.checkHeartbeats() // First call to populate heartbeats
        sut.checkHeartbeats() // Second call to trigger removal

        // Assert
        verify(exactly = 2) {
            redisTemplate.keys(any())
            redisTemplate.opsForValue()
            valueOps.get(serverAddress)
        }
        verify(exactly = 1) { storeRemoveService.removeStoreServer(serverAddress) }
    }

    @Test
    fun `sut should not remove server`() {
        // Arrange
        val serverAddress = "serverAddress"
        val valueOps = mockk<ValueOperations<String, String>>()
        every { redisTemplate.keys(any()) } returns setOf(serverAddress)
        every { redisTemplate.opsForValue() } returns valueOps
        every { valueOps.get(serverAddress) } returnsMany listOf("122","123")
        justRun { storeRemoveService.removeStoreServer(serverAddress) }

        // Act
        sut.checkHeartbeats() // First call to populate heartbeats
        sut.checkHeartbeats() // Second call to trigger removal

        // Assert
        verify(exactly = 2) {
            redisTemplate.keys(any())
            redisTemplate.opsForValue()
            valueOps.get(serverAddress)
        }
        verify { storeRemoveService wasNot Called }
    }
}
