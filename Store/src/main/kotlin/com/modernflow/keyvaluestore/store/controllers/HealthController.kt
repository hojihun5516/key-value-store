package com.modernflow.keyvaluestore.store.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthController {
    @GetMapping("/health-check")
    fun isHealth(): Boolean {
        return true
    }
}
