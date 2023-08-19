package com.modernflow.keyvaluestore.servicediscovery.clients

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable

@FeignClient(
    name = "proxyClient",
    url = "proxy-service:9000",
)
interface ProxyClient {
    @DeleteMapping("/proxy/consistence-hash-map/ip/{ip}/port/{port}")
    fun delete(@PathVariable ip: String, @PathVariable port: String)
}
