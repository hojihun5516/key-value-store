package com.modernflow.keyvaluestore.proxy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication(scanBasePackages = ["com.modernflow.keyvaluestore"])
@EnableFeignClients(basePackages = ["com.modernflow"])
class ProxyApplication

fun main(args: Array<String>) {
	runApplication<ProxyApplication>(*args)
}
