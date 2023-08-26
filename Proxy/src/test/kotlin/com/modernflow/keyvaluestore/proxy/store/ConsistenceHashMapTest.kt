package com.modernflow.keyvaluestore.proxy.store

import com.modernflow.keyvaluestore.dtos.PhysicalNodeAddressDto
import com.modernflow.keyvaluestore.dtos.VirtualNode
import com.modernflow.keyvaluestore.proxy.hash.Hash
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.util.ReflectionTestUtils
import java.util.*

@ExtendWith(MockKExtension::class)
class ConsistenceHashMapTest(
    @MockK private val hash: Hash
) {

    @Test
    @DisplayName("virtual node가 300개 들어있는 circle을 만든다")
    fun `sut should create circle size of 300`() {
        // Arrange
        val randomValues = List(300) { Random().nextLong() }
        every { hash.hash(any()) } returnsMany randomValues

        val sut = ConsistenceHashMap(hash)
        sut.createCirCle(getPhysicalAddresses())

        // Act
        val circle = ReflectionTestUtils.getField(sut, "circle") as SortedMap<*, *>

        // Assert
        assertThat(circle.size).isEqualTo(300)
    }

    @Test
    @DisplayName("key가 주어졌을 때 physical node를 리턴한다")
    fun `sut should get correct physical node when key is given`() {
        // Arrange
        every { hash.hash(any()) } returnsMany List(300) { Random().nextLong() }
        val sut = ConsistenceHashMap(hash)
        sut.createCirCle(getPhysicalAddresses())

        val key = "testKey"
        val hashValue: Long = 1234 // example hash value
        every { hash.hash(key) } returns hashValue

        // Act
        val physicalNodeAddressDto = sut.getPhysicalNode(key)

        // Assert
        assertThat(physicalNodeAddressDto).isNotNull
    }

    @Test
    @DisplayName("key가 주어졌을 때 key의 해시값보다 큰 virtual node가 없다면 첫번째 node를 리턴한다")
    fun `sut should return first node when correct virtual node is not exists`() {
        // Arrange
        every { hash.hash(any()) } returnsMany List(300) { Random().nextLong() }
        val sut = ConsistenceHashMap(hash)
        sut.createCirCle(getPhysicalAddresses())

        val key = "testKey"
        val hashValue: Long = Long.MAX_VALUE // example hash value
        every { hash.hash(key) } returns hashValue

        val circle = ReflectionTestUtils.getField(sut, "circle") as SortedMap<*, *>
        val expectedPhysicalNode = (circle[circle.firstKey()] as VirtualNode).physicalNode

        // Act
        val actual = sut.getPhysicalNode(key)

        // Assert
        assertThat(actual).isEqualTo(expectedPhysicalNode)
    }

    @Test
    @DisplayName("제거할 physicalNode 정보를 받아서 해당하는 가상노드를 전부 제거한다")
    fun `sut should remove phyiscalNode on circle`() {
        // Arrange
        every { hash.hash(any()) } returnsMany List(300) { Random().nextLong() }
        val sut = ConsistenceHashMap(hash)
        sut.createCirCle(getPhysicalAddresses())
        val removedTargetPhysicalNode = PhysicalNodeAddressDto(
            ip = "store-service-1",
            port = 5000,
        )

        // Act
        sut.removePhysicalNode(removedTargetPhysicalNode)

        // Assert
        val circle = ReflectionTestUtils.getField(sut, "circle") as SortedMap<Long, VirtualNode>

        assertFalse(circle.values.any { it.physicalNode == removedTargetPhysicalNode })
    }

    private fun getPhysicalAddresses(): List<PhysicalNodeAddressDto> {
        return listOf(
            PhysicalNodeAddressDto(
                ip = "store-service-1",
                port = 5000,
            ),
            PhysicalNodeAddressDto(
                ip = "store-service-2",
                port = 5100,
            ),
            PhysicalNodeAddressDto(
                ip = "store-service-3",
                port = 5200,
            )
        )
    }
}
