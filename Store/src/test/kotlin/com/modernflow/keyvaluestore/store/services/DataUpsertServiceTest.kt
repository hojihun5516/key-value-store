package com.modernflow.keyvaluestore.store.services

import com.modernflow.keyvaluestore.dtos.KeyValueStoreDto
import com.modernflow.keyvaluestore.store.storage.DataManager
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class DataUpsertServiceTest(
    @MockK private val dataManager: DataManager
) {
    @InjectMockKs
    private lateinit var sut: DataUpsertService

    @Test
    fun `sut should upsert data and return boolean when key is given`() {
        // Arrange
        every { dataManager.upsert(key = 12345L, value = "value") } returns true

        // Act
        val actual = sut.upsert(KeyValueStoreDto(key = 12345L, value = "value"))

        // Assert
        Assertions.assertThat(actual).isTrue()
    }
}
