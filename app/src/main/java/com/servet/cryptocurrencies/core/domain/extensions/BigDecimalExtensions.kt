package com.servet.cryptocurrencies.core.domain.extensions

import java.math.BigDecimal

fun String.toBigDecimalSafe(default: BigDecimal = BigDecimal.ZERO): BigDecimal =
    runCatching { this.toBigDecimal() }.getOrElse { default }