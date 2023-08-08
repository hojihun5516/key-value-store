package com.modernflow.keyvaluestore.servicediscovery.services

import com.modernflow.keyvaluestore.dtos.PhysicalNodeAddressDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

@Service
class ProxyService(private val restTemplate: RestTemplate) {
    suspend fun removeStoreServer(physicalNodeAddressDto: PhysicalNodeAddressDto) {
        val (ip, port) = physicalNodeAddressDto
        val removeUrl = "http://proxy-service:9000/proxy/consistence-hash-map/ip/$ip/port/$port"

        try {
            withContext(Dispatchers.IO) {
                restTemplate.delete(removeUrl)
            }
            println("A 스토어 서버 제거 요청이 성공적으로 완료되었습니다.")
        } catch (ex: HttpClientErrorException) {
            if (ex.statusCode == HttpStatus.NOT_FOUND) {
                println("A 스토어 서버를 찾을 수 없습니다.")
            } else {
                println("A 스토어 서버 제거 요청이 실패하였습니다. 응답코드: ${ex.statusCode.value()}")
            }
        } catch (ex: Exception) {
            println("A 스토어 서버 제거 요청 중에 예외가 발생하였습니다: ${ex.message}")
        }
    }
}
