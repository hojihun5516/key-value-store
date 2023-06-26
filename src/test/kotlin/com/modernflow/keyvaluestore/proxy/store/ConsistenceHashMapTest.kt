package com.modernflow.keyvaluestore.proxy.store

import com.modernflow.keyvaluestore.proxy.hash.Hash
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
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
    @DisplayName("virtual node가 100개 들어있는 circle을 만든다")
    fun `sut should create circle size of 100`() {
        // Arrange
        val randomValues = List(100) { Random().nextLong() }
        every { hash.hash(any()) } returnsMany randomValues

        val sut = ConsistenceHashMap(hash)

        // Act
        val circle = ReflectionTestUtils.getField(sut, "circle") as SortedMap<*, *>

        // Assert
        assertThat(circle.size).isEqualTo(100)
    }

    @Test
    @DisplayName("key가 주어졌을 때 key의 해시값보다 큰 virtual node를 리턴한다")
    fun `sut should get correct virtual node when key is given`() {
        // Arrange
        every { hash.hash(any()) } returnsMany List(100) { Random().nextLong() }
        val sut = ConsistenceHashMap(hash)

        val key = "testKey"
        val hashValue: Long = 1234 // example hash value
        every { hash.hash(key) } returns hashValue

        // Act
        val virtualNode = sut.getVirtualNode(key)

        // Assert
        assertThat(virtualNode).isNotNull
        assertThat(virtualNode.hash).isGreaterThanOrEqualTo(hashValue)
    }

    @Test
    @DisplayName("key가 주어졌을 때 key의 해시값보다 큰 virtual noder가 없다면 첫번째 virtual node를 리턴한다")
    fun `sut should return first virtual node when correct virtual node is not exists`() {
        // Arrange
        every { hash.hash(any()) } returnsMany List(100) { Random().nextLong() }
        val sut = ConsistenceHashMap(hash)

        val key = "testKey"
        val hashValue: Long = Long.MAX_VALUE // example hash value
        every { hash.hash(key) } returns hashValue

        // Act
        val virtualNode = sut.getVirtualNode(key)

        // Assert
        assertThat(virtualNode).isNotNull
        assertThat(virtualNode.hash).isLessThan(hashValue)
    }
}
