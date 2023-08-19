package com.modernflow.keyvaluestore.servicediscovery.address

import com.modernflow.keyvaluestore.address.PhysicalAddress
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class PhysicalAddressTest {

    private val sut = PhysicalAddress

    @Test
    @DisplayName("사용가능한 물리노드를 제거한다")
    fun `sut remove available physical address`() {
        // Arrange

        // Act
        sut.removePhysicalNode(sut.FIRST_PHYSICAL_NODE.ip, sut.FIRST_PHYSICAL_NODE.port)

        // Assert
        assertThat(sut.availablePhysicalAddresses).hasSize(2)
    }
}
