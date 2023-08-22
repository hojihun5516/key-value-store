package com.modernflow.keyvaluestore.store

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication(scanBasePackages = ["com.modernflow.keyvaluestore"])
@EnableFeignClients(basePackages = ["com.modernflow"])
@EnableScheduling
class StoreApplication

fun main(args: Array<String>) {
	runApplication<StoreApplication>(*args)
}
