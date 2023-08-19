package com.modernflow.keyvaluestore.servicediscovery.services

import com.modernflow.keyvaluestore.clients.FirstStoreClient
import com.modernflow.keyvaluestore.clients.SecondStoreClient
import com.modernflow.keyvaluestore.clients.ThirdStoreClient
import com.modernflow.keyvaluestore.dtos.PhysicalNodeAddressDto
import com.modernflow.keyvaluestore.address.PhysicalAddress.FIRST_PHYSICAL_NODE
import com.modernflow.keyvaluestore.address.PhysicalAddress.SECOND_PHYSICAL_NODE
import com.modernflow.keyvaluestore.address.PhysicalAddress.THIRD_PHYSICAL_NODE
import com.modernflow.keyvaluestore.services.PhysicalAddressClientService
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock

@ExtendWith(MockKExtension::class)
class PhysicalAddressClientServiceTest(
    @MockK private val firstStoreClient: FirstStoreClient,
    @MockK private val secondStoreClient: SecondStoreClient,
    @MockK private val thirdStoreClient: ThirdStoreClient,
) {
    @InjectMockKs
    private lateinit var sut: PhysicalAddressClientService

    @Test
    fun `sut should return storeClient when target physical node is given`() {
        // Arrange
        val firstPhysicalNode = FIRST_PHYSICAL_NODE
        val secondPhysicalNode = SECOND_PHYSICAL_NODE
        val thirdPhysicalNode = THIRD_PHYSICAL_NODE

        // Act
        val firstStoreClientResult = sut.getStoreClient(firstPhysicalNode)
        val secondStoreClientResult = sut.getStoreClient(secondPhysicalNode)
        val thirdStoreClientResult = sut.getStoreClient(thirdPhysicalNode)

        // Assert
        assertEquals(firstStoreClient, firstStoreClientResult)
        assertEquals(secondStoreClient, secondStoreClientResult)
        assertEquals(thirdStoreClient, thirdStoreClientResult)
    }

    @Test
    fun `sut throw exception when PhysicalNodeAddressDto is not found`() {
        // Arrange
        val firstStoreClient = mock(FirstStoreClient::class.java)
        val secondStoreClient = mock(SecondStoreClient::class.java)
        val thirdStoreClient = mock(ThirdStoreClient::class.java)
        val physicalAddressClientService = PhysicalAddressClientService(
            firstStoreClient,
            secondStoreClient,
            thirdStoreClient
        )

        val unknownPhysicalNode = PhysicalNodeAddressDto("unknownIp", 0)

        // Act & Assert
        assertThrows(Exception::class.java) {
            physicalAddressClientService.getStoreClient(unknownPhysicalNode)
        }
    }
}
