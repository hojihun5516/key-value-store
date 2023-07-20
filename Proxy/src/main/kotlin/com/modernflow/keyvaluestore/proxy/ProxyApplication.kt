package com.modernflow.keyvaluestore.proxy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = ["com.modernflow.keyvaluestore"])
@EnableFeignClients(basePackages = ["com.modernflow"])
class ProxyApplication

fun main(args: Array<String>) {
	runApplication<ProxyApplication>(*args)
}
