package com.example.keyvaluestore.hash

import org.springframework.stereotype.Component

/**
 * Murmur hash 3.0.
 *
 *
 * It works as the same as lastguest\Murmur in PHP in limited test data.
 */
@Component
class MurmurHash : Hash {
    /**
     * @param key Text to hash.
     * @param seed   Positive integer only
     * @return number 32-bit positive integer hash(No unsigned-int in Java, use Long)
     */
    override fun hash(key: String, seed: Int): Long {
        val bytes = padKey(key).toByteArray()
        val klen = bytes.size
        var h1 = (if (seed < 0) -seed else seed).toLong() // positive seed
        var remainder = 0
        var i = 0
        var k1: Long
        val mlen = klen - (klen and 3.also { remainder = it })
        while (i < mlen) {
            k1 = (bytes[i]
                .toInt()
                or (bytes[++i].toInt() shl 8)
                or (bytes[++i].toInt() shl 16)
                or (bytes[++i].toInt() shl 24)).toLong()
            ++i
            k1 =
                (k1 and 0xffffL) * -0x3361d2af + ((if (k1 >= 0) k1 shr 16 else k1 and 0x7fffffffL shr 16 or 0x8000L) * -0x3361d2af and 0xffffL shl 16) and 0xffffffffL
            k1 = k1 shl 15 or if (k1 >= 0) k1 shr 17 else k1 and 0x7fffffffL shr 17 or 0x4000L
            k1 =
                (k1 and 0xffffL) * 0x1b873593 + ((if (k1 >= 0) k1 shr 16 else k1 and 0x7fffffffL shr 16 or 0x8000L) * 0x1b873593 and 0xffffL shl 16) and 0xffffffffL
            h1 = h1 xor k1
            h1 = h1 shl 13 or if (h1 >= 0) h1 shr 19 else h1 and 0x7fffffffL shr 19 or 0x1000L
            val h1b =
                (h1 and 0xffffL) * 5 + ((if (h1 >= 0) h1 shr 16 else h1 and 0x7fffffffL shr 16 or 0x8000L) * 5 and 0xffffL shl 16) and 0xffffffffL
            h1 =
                (h1b and 0xffffL) + 0x6b64 + ((if (h1b >= 0) h1b shr 16 else h1b and 0x7fffffffL shr 16 or 0x8000L) + 0xe654 and 0xffffL shl 16)
        }
        k1 = 0
        when (remainder) {
            3 -> {
                k1 = k1 xor (bytes[i + 2].toInt() shl 16).toLong()
                k1 = k1 xor (bytes[i + 1].toInt() shl 8).toLong()
                k1 = k1 xor bytes[i].toLong()
                k1 =
                    (k1 and 0xffffL) * -0x3361d2af + ((if (k1 >= 0) k1 shr 16 else k1 and 0x7fffffffL shr 16 or 0x8000L) * -0x3361d2af and 0xffffL shl 16) and 0xffffffffL
                k1 = k1 shl 15 or if (k1 >= 0) k1 shr 17 else k1 and 0x7fffffffL shr 17 or 0x4000L
                k1 =
                    (k1 and 0xffffL) * 0x1b873593 + ((if (k1 >= 0) k1 shr 16 else k1 and 0x7fffffffL shr 16 or 0x8000L) * 0x1b873593 and 0xffffL shl 16) and 0xffffffffL
                h1 = h1 xor k1
            }

            2 -> {
                k1 = k1 xor (bytes[i + 1].toInt() shl 8).toLong()
                k1 = k1 xor bytes[i].toLong()
                k1 =
                    (k1 and 0xffffL) * -0x3361d2af + ((if (k1 >= 0) k1 shr 16 else k1 and 0x7fffffffL shr 16 or 0x8000L) * -0x3361d2af and 0xffffL shl 16) and 0xffffffffL
                k1 = k1 shl 15 or if (k1 >= 0) k1 shr 17 else k1 and 0x7fffffffL shr 17 or 0x4000L
                k1 =
                    (k1 and 0xffffL) * 0x1b873593 + ((if (k1 >= 0) k1 shr 16 else k1 and 0x7fffffffL shr 16 or 0x8000L) * 0x1b873593 and 0xffffL shl 16) and 0xffffffffL
                h1 = h1 xor k1
            }

            1 -> {
                k1 = k1 xor bytes[i].toLong()
                k1 =
                    (k1 and 0xffffL) * -0x3361d2af + ((if (k1 >= 0) k1 shr 16 else k1 and 0x7fffffffL shr 16 or 0x8000L) * -0x3361d2af and 0xffffL shl 16) and 0xffffffffL
                k1 = k1 shl 15 or if (k1 >= 0) k1 shr 17 else k1 and 0x7fffffffL shr 17 or 0x4000L
                k1 =
                    (k1 and 0xffffL) * 0x1b873593 + ((if (k1 >= 0) k1 shr 16 else k1 and 0x7fffffffL shr 16 or 0x8000L) * 0x1b873593 and 0xffffL shl 16) and 0xffffffffL
                h1 = h1 xor k1
            }
        }
        h1 = h1 xor klen.toLong()
        h1 = h1 xor if (h1 >= 0) h1 shr 16 else h1 and 0x7fffffffL shr 16 or 0x8000L
        h1 =
            (h1 and 0xffffL) * -0x7a143595 + ((if (h1 >= 0) h1 shr 16 else h1 and 0x7fffffffL shr 16 or 0x8000L) * -0x7a143595 and 0xffffL shl 16) and 0xffffffffL
        h1 = h1 xor if (h1 >= 0) h1 shr 13 else h1 and 0x7fffffffL shr 13 or 0x40000L
        h1 =
            (h1 and 0xffffL) * -0x3d4d51cb + ((if (h1 >= 0) h1 shr 16 else h1 and 0x7fffffffL shr 16 or 0x8000L) * -0x3d4d51cb and 0xffffL shl 16) and 0xffffffffL
        h1 = h1 xor if (h1 >= 0) h1 shr 16 else h1 and 0x7fffffffL shr 16 or 0x8000L
        return h1
    }

    private fun padKey(key: String): String {
        var modifiedKey = key
        while (modifiedKey.length < 7) {
            modifiedKey += " "
        }
        return modifiedKey
    }
}
