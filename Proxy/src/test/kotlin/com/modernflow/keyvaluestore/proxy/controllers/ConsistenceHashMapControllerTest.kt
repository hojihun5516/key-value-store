package com.modernflow.keyvaluestore.proxy.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.modernflow.keyvaluestore.dtos.PhysicalNodeAddressDto
import com.modernflow.keyvaluestore.proxy.store.ConsistenceHashMap
import com.ninjasquad.springmockk.MockkBean
import io.mockk.justRun
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@WebMvcTest(ConsistenceHashMapController::class)
class ConsistenceHashMapControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    @MockkBean private val consistenceHashMap: ConsistenceHashMap,
){
    @Test
    fun `sut should delete physical node on consistence hash map when ip and port are given`() {
        // Arrange
        val ip = "store-service-1"
        val port = 5000
        justRun { consistenceHashMap.removePhysicalNode(PhysicalNodeAddressDto(ip, port)) }

        // Act & Assert
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/proxy/consistence-hash-map/ip/$ip/port/$port")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)

        verify { consistenceHashMap.removePhysicalNode(PhysicalNodeAddressDto(ip, port)) }
    }
}