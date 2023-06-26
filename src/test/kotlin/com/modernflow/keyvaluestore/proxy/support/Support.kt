package com.modernflow.keyvaluestore.proxy.support

import com.appmattus.kotlinfixture.config.range
import com.appmattus.kotlinfixture.decorator.nullability.NeverNullStrategy
import com.appmattus.kotlinfixture.decorator.nullability.nullabilityStrategy
import com.appmattus.kotlinfixture.decorator.optional.AlwaysOptionalStrategy
import com.appmattus.kotlinfixture.decorator.optional.optionalStrategy
import com.appmattus.kotlinfixture.decorator.recursion.NullRecursionStrategy
import com.appmattus.kotlinfixture.decorator.recursion.recursionStrategy
import com.appmattus.kotlinfixture.kotlinFixture

object Support {

    val fixture = kotlinFixture {
        recursionStrategy(NullRecursionStrategy)
        optionalStrategy(AlwaysOptionalStrategy)
        nullabilityStrategy(NeverNullStrategy)
        factory<Int> { range(1..10) }
        factory<Long> { range(1L..10L) }
    }
}
