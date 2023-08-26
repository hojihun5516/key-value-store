package com.modernflow.keyvaluestore.store.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.modernflow.keyvaluestore.dtos.HashedKeyValueStoreDto
import com.modernflow.keyvaluestore.dtos.StoreValueDto
import com.modernflow.keyvaluestore.store.services.DataDeleteService
import com.modernflow.keyvaluestore.store.services.DataGetService
import com.modernflow.keyvaluestore.store.services.DataUpsertService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(StoreController::class)
class StoreControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    @MockkBean private val dataUpsertService: DataUpsertService,
    @MockkBean private val dataDeleteService: DataDeleteService,
    @MockkBean private val dataGetService: DataGetService,
) {
    @Test
    fun `sut should upsert data`() {
        // Arrange
        val key = 123L
        val value = "VALUE-1"
        val request = StoreValueDto(value)
        every { dataUpsertService.upsert(HashedKeyValueStoreDto(key, value)) } returns true

        // Act & Assert
        mockMvc.perform(
            put("/store/$key")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request)),
        )
            .andExpect(status().isOk)

        verify { dataUpsertService.upsert(HashedKeyValueStoreDto(key, value)) }
    }

    @Test
    fun `sut should get data`() {
        // Arrange
        val key = 123L
        val value = "VALUE"
        every { dataGetService.get(key) } returns value

        // Act & Assert
        mockMvc.perform(
            get("/store/$key")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.value").value("VALUE"))

        verify { dataGetService.get(key) }
    }

    @Test
    fun `sut should delete data`() {
        // Arrange
        val key = 123L
        every { dataDeleteService.delete(key) } returns true

        // Act & Assert
        mockMvc.perform(
            delete("/store/$key")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
            .andExpect(status().isOk)

        verify { dataDeleteService.delete(key) }
    }
}