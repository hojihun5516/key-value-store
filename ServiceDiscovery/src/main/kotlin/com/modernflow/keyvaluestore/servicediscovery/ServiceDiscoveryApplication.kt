package com.modernflow.keyvaluestore.servicediscovery

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@EnableFeignClients(basePackages = ["com.modernflow"])
@ComponentScan(basePackages = ["com.modernflow.keyvaluestore"])
class ServiceDiscoveryApplication

fun main(args: Array<String>) {
    runApplication<ServiceDiscoveryApplication>(*args)
}
