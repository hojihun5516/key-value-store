package com.modernflow.keyvaluestore.proxy.store

data class VirtualNode(
    val id: String,
    val hash: Long,
    // TODO physical node 생성
//    val physicalNode: PhysicalNode,
)
