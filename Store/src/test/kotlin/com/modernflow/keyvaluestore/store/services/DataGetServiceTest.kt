package com.modernflow.keyvaluestore.store.services

import com.modernflow.keyvaluestore.store.storage.DataManager
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class DataGetServiceTest(
    @MockK private val dataManager: DataManager
) {
    @InjectMockKs
    private lateinit var sut: DataGetService

    @Test
    fun `sut should get data when key is given`() {
        // Arrange
        val expectedValue = "Value"
        every { dataManager.get(12345L) } returns expectedValue

        // Act
        val actual = sut.get(12345L)

        // Assert
        assertThat(actual).isEqualTo(expectedValue)
    }
}
