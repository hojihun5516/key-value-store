package com.modernflow.keyvaluestore.configs

import feign.Request
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit

@Configuration
class FeignClientConfig {
    private val connectTimeout = 1L
    private val readTimeout = 3000L

    @Bean
    fun options(): Request.Options {
        return Request.Options(connectTimeout, TimeUnit.MILLISECONDS, readTimeout, TimeUnit.MILLISECONDS, false)
    }
}