package com.modernflow.keyvaluestore.proxy.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.modernflow.keyvaluestore.dtos.KeyValueStoreGetResponseDto
import com.modernflow.keyvaluestore.dtos.KeyValueStoreRequestDto
import com.modernflow.keyvaluestore.dtos.StoreUpsertRequestDto
import com.modernflow.keyvaluestore.proxy.services.StoreDeleteService
import com.modernflow.keyvaluestore.proxy.services.StoreGetService
import com.modernflow.keyvaluestore.proxy.services.StoreUpsertService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(StoreController::class)
class StoreControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    @MockkBean private val storeUpsertService: StoreUpsertService,
    @MockkBean private val storeGetService: StoreGetService,
    @MockkBean private val storeDeleteService: StoreDeleteService,
) {
    @Test
    fun `sut should upsert data`() {
        // Arrange
        val key = "TEST-KEY"
        val value = "VALUE-1"
        val request = StoreUpsertRequestDto(value)

        every {
            storeUpsertService.upsert(keyValueStoreRequestDto = KeyValueStoreRequestDto(
                key = key,
                value = request.value
            ))
        } returns true

        // Act & Assert
        mockMvc.perform(
            MockMvcRequestBuilders.put("/proxy/store/$key")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)),
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string("true"))

        verify {
            storeUpsertService.upsert(KeyValueStoreRequestDto(
                key = key,
                value = request.value
            ))
        }
    }

    @Test
    fun `sut should get data`() {
        // Arrange
        val key = "TEST-KEY"
        val value = "VALUE"
        val expected = KeyValueStoreGetResponseDto(key = key, value = value)
        every { storeGetService.get(key) } returns expected

        // Act & Assert
        mockMvc.perform(
            MockMvcRequestBuilders.get("/proxy/store/$key")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.key").value(expected.key))
            .andExpect(MockMvcResultMatchers.jsonPath("$.value").value(expected.value))

        verify { storeGetService.get(key) }
    }

    @Test
    fun `sut should delete data`() {
        // Arrange
        val key = "TEST-KEY"
        every { storeDeleteService.delete(key) } returns true

        // Act & Assert
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/proxy/store/$key")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string("true"))

        verify { storeDeleteService.delete(key) }
    }
}
