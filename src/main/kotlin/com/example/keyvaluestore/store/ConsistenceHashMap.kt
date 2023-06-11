package com.example.keyvaluestore.store

import com.example.keyvaluestore.hash.Hash
import org.springframework.stereotype.Component
import java.util.*

@Component
class ConsistenceHashMap(
    private val hash: Hash,
) {
    private val numberOfVirtualNode: Int = 100
    private val circle: SortedMap<Long, VirtualNode> = TreeMap()

    init {
        createCirCle()
    }

    private fun createCirCle() {
        for (i in 0 until numberOfVirtualNode) {
            val id = UUID.randomUUID().toString()
            val hashed = hash.hash(id)
            val virtualNode = VirtualNode(id = id, hash = hashed)
            circle[hashed] = virtualNode
        }
    }
}
