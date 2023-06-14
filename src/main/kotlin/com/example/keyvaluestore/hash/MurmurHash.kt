package com.example.keyvaluestore.hash

import com.google.common.hash.Hashing
import org.springframework.stereotype.Component

@Component
class MurmurHash : Hash {
    override fun hash(key: String): Long {
        return Hashing.murmur3_128()
            .hashString(key, Charsets.UTF_8)
            .asLong()
    }
}
