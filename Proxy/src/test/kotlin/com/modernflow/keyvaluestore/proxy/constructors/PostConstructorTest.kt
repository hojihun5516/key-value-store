package com.modernflow.keyvaluestore.proxy.constructors

import com.modernflow.keyvaluestore.address.PhysicalAddress
import com.modernflow.keyvaluestore.proxy.store.ConsistenceHashMap
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class PostConstructorTest(
    @MockK private val consistenceHashMap: ConsistenceHashMap,
) {
    @InjectMockKs
    private lateinit var sut: PostConstructor

    @Test
    fun `sut should initialize consistenceHashMap`() {
        // Arrange
        every { consistenceHashMap.createCirCle(PhysicalAddress.availablePhysicalAddresses) } returns true

        // Act
        val actual = sut.init()

        // Assert
        verify { consistenceHashMap.createCirCle(PhysicalAddress.availablePhysicalAddresses) }
        assertThat(actual).isTrue
    }
}
