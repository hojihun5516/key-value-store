package com.modernflow.keyvaluestore.proxy.hash

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class MurmurHashTest {
    private val sut: Hash = MurmurHash()

    @ParameterizedTest
    @ValueSource(strings = ["a", "abcde123123", "abcdefg", "12345"])
    @DisplayName("문자열로 들어온 키를 해쉬한다")
    fun `sut should hash data`(key: String) {
        //Arrange

        //Act
        val actual = sut.hash(key)

        // Assert
        assertThat(actual).isNotNull()
    }
}
