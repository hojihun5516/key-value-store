package com.modernflow.keyvaluestore.servicediscovery.services

import com.modernflow.keyvaluestore.servicediscovery.clients.StoreClient
import com.modernflow.keyvaluestore.servicediscovery.dtos.PhysicalNodeAddressDto
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class HealthCheckServiceTest(
    @MockK private val physicalAddressClientService: PhysicalAddressClientService,
    @MockK private val proxyService: ProxyService,
) {
    @InjectMockKs
    private lateinit var sut: HealthCheckService

    @Test
    fun `sut should return true when StoreClient healthCheck returns true`() {
        // Arrange
        val storeClient = mockk<StoreClient>()
        val physicalNodeAddressDto = PhysicalNodeAddressDto(ip = "localhost", port = 5000)
        every { physicalAddressClientService.getStoreClient(physicalNodeAddressDto) } returns storeClient
        every { storeClient.healthCheck() } returns true

        // Act
        val actual = sut.isHealth(physicalNodeAddressDto)

        // Assert
        assertThat(actual).isTrue()
    }
}
