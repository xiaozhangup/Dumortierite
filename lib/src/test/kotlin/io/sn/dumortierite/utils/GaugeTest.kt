package io.sn.dumortierite.utils

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class GaugeTest {

    @Test
    fun testInsertGauge() {
        val lengthHalf = 10
        val separator = '/'
        val progress = "|"

        println(Gauge(lengthHalf, separator, progress, "1 J", "1500 J"))
        println(Gauge(lengthHalf, separator, progress, "20 J", "1500 J"))
        println(Gauge(lengthHalf, separator, progress, "300 J", "1500 J"))
        println(Gauge(lengthHalf, separator, progress, "1200 J", "1500 J"))
        assertTrue(true)
    }
}
