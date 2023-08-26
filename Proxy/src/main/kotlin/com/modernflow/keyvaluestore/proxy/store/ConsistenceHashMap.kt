package com.modernflow.keyvaluestore.proxy.store

import com.modernflow.keyvaluestore.dtos.PhysicalNodeAddressDto
import com.modernflow.keyvaluestore.dtos.VirtualNode
import com.modernflow.keyvaluestore.proxy.hash.Hash
import org.springframework.stereotype.Component
import java.util.*

@Component
class ConsistenceHashMap(
    private val hash: Hash,
) {
    private val numberOfVirtualNode: Int = 300
    private val circle: SortedMap<Long, VirtualNode> = TreeMap()

    fun removePhysicalNode(physicalNodeAddressDto: PhysicalNodeAddressDto) {
        circle.entries.removeIf { it.value.physicalNode == physicalNodeAddressDto }
    }

    fun hashKey(key: String): Long = hash.hash(key)

    fun createCirCle(storeAddresses: List<PhysicalNodeAddressDto>): Boolean {
        if (circle.isNotEmpty() or storeAddresses.isEmpty()) {
            return false
        }

        val numberOfVirtualNode = numberOfVirtualNode / storeAddresses.size

        storeAddresses.forEach { storeAddress ->
            repeat(numberOfVirtualNode) {
                val id = UUID.randomUUID().toString()
                val hashed = hash.hash(id)
                val virtualNode = VirtualNode(id = id, hash = hashed, storeAddress)
                circle[hashed] = virtualNode
            }
        }
        return true
    }

    fun getPhysicalNode(key: String): PhysicalNodeAddressDto {
        val hash = hash.hash(key)
        return findPhysicalNodeByHash(hash) ?: getFirstPhysicalNode()
    }

    private fun findPhysicalNodeByHash(hash: Long): PhysicalNodeAddressDto? {
        return circle.entries
            .firstOrNull { it.key >= hash }
            ?.value
            ?.physicalNode
    }

    private fun getFirstPhysicalNode(): PhysicalNodeAddressDto {
        return circle.entries.first().value.physicalNode
    }
}
