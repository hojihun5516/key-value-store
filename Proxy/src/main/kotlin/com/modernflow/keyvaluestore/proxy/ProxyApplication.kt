package com.modernflow.keyvaluestore.proxy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ProxyApplication

fun main(args: Array<String>) {
	runApplication<ProxyApplication>(*args)
}
