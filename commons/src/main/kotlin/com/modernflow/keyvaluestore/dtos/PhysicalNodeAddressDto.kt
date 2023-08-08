package com.modernflow.keyvaluestore.dtos

data class PhysicalNodeAddressDto(
    val ip: String,
    val port: Int,
) {
    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is PhysicalNodeAddressDto) return false
        return ip == other.ip && port == other.port
    }
    override fun toString(): String {
        return "ip = $ip, port = $port"
    }
}
