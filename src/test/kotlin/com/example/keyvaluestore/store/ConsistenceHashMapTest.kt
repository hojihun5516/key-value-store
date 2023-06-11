package com.example.keyvaluestore.store

import com.example.keyvaluestore.hash.Hash
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
}
