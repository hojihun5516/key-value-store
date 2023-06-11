package com.example.keyvaluestore.hash

interface Hash {
    fun hash(key: String, seed: Int = 10): Long

}
