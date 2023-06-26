package com.modernflow.keyvaluestore.servicediscovery

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ServicediscoveryApplication

fun main(args: Array<String>) {
    runApplication<ServicediscoveryApplication>(*args)
}
