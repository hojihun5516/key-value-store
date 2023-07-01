package com.modernflow.keyvaluestore.store.services

import com.modernflow.keyvaluestore.store.storage.DataManager
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class DataDeleteServiceTest(
    @MockK private val dataManager: DataManager
) {
    @InjectMockKs
    private lateinit var sut: DataDeleteService

    @Test
    fun `sut should delete and return boolean when key is given`() {
        // Arrange
        val expectedValue = "Value"
        every { dataManager.delete(12345L) } returns true

        // Act
        val actual = sut.delete(12345L)

        // Assert
        Assertions.assertThat(actual).isTrue()
    }
}
