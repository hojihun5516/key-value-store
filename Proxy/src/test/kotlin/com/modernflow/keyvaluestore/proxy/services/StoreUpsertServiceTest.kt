package com.modernflow.keyvaluestore.proxy.services

import com.modernflow.keyvaluestore.clients.StoreClient
import com.modernflow.keyvaluestore.dtos.KeyValueStoreDto
import com.modernflow.keyvaluestore.dtos.PhysicalNodeAddressDto
import com.modernflow.keyvaluestore.dtos.StoreValueDto
import com.modernflow.keyvaluestore.dtos.VirtualNode
import com.modernflow.keyvaluestore.proxy.store.ConsistenceHashMap
import com.modernflow.keyvaluestore.services.PhysicalAddressClientService
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class StoreUpsertServiceTest(
    @MockK private val consistenceHashMap: ConsistenceHashMap,
    @MockK private val physicalAddressClientService: PhysicalAddressClientService,
) {
    @InjectMockKs
    private lateinit var sut: StoreUpsertService

    @Test
    fun `sut should upsert successfully`() {
        // Arrange
        val key = "testKey"
        val value = "testValue"
        val hashedKey = 123L
        val physicalNode = PhysicalNodeAddressDto("store-service-1", 5000)
        val storeClient = mockk<StoreClient>()

        every { consistenceHashMap.hashKey(key) } returns hashedKey
        every { consistenceHashMap.getPhysicalNode(key) } returns physicalNode
        every { physicalAddressClientService.getStoreClient(physicalNode) } returns storeClient
        every { storeClient.upsert(hashedKey, StoreValueDto(value)) } returns true

        // Act
        val actual = sut.upsert(KeyValueStoreDto(key, value))

        // Assert
        assertThat(actual).isTrue
        verify {
            consistenceHashMap.hashKey(key)
            consistenceHashMap.getPhysicalNode(key)
            physicalAddressClientService.getStoreClient(physicalNode)
            storeClient.upsert(hashedKey, StoreValueDto(value))
        }
    }

    @Test
    fun `sut should throw exception when upsert fails`() {
        // Arrange
        val key = "testKey"
        val value = "testValue"
        val hashedKey = 123L
        val physicalNode = PhysicalNodeAddressDto("store-service-1", 5000)
        val keyValueStoreDto = KeyValueStoreDto(key, value)
        val storeClient = mockk<StoreClient>()

        every { consistenceHashMap.hashKey(key) } returns hashedKey
        every { consistenceHashMap.getPhysicalNode(key) } returns physicalNode
        every { physicalAddressClientService.getStoreClient(any()) } returns storeClient
        every { storeClient.upsert(hashedKey, StoreValueDto(value)) } throws Exception("upsert error")

        // Act & Assert
        assertThrows<Exception> { sut.upsert(keyValueStoreDto) }
        verify {
            consistenceHashMap.hashKey(key)
            consistenceHashMap.getPhysicalNode(key)
            physicalAddressClientService.getStoreClient(physicalNode)
            storeClient.upsert(hashedKey, StoreValueDto(value))
        }
    }
}
