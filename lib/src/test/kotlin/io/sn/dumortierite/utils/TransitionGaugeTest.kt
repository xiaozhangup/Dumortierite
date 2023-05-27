package io.sn.dumortierite.utils

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TransitionGaugeTest {

    @Test
    fun testGauge() {
        val lengthHalf= 10
        val separator = '/'
        val progress = "|"
        val unit = "J"

        assertEquals("||||||| 1 J / 1500 J ||||", TransitionGauge(lengthHalf, separator, progress, 1F, 1500F, unit).toString())
        assertEquals("|||||| 10 J / 1500 J ||||", TransitionGauge(lengthHalf, separator, progress, 10F, 1500F, unit).toString())
        assertEquals("||||| 100 J / 1500 J ||||", TransitionGauge(lengthHalf, separator, progress, 100F, 1500F, unit).toString())
        assertEquals("|||| 1000 J / 1500 J ||||", TransitionGauge(lengthHalf, separator, progress, 1000F, 1500F, unit).toString())
    }

}
