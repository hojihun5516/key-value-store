package com.modernflow.keyvaluestore.store.controllers

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController {
    @GetMapping(
        "/health-check",
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun isHealth(): Boolean {
        return true
    }
}
