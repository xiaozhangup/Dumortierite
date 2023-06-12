@file:Suppress("unused")

package io.sn.dumortierite.utils

import io.sn.dumortierite.DumoCore
import net.kyori.adventure.text.Component

open class TransitionGauge(
    private val lengthInHalf: Int,
    private val separator: Char,
    private val progress: CharSequence,
    private val currentVal: Float,
    private val maxVal: Float,
    private val unit: String,
) {

    override fun toString(): String {
        val currentVal = "${currentVal.toInt()} $unit"
        val maxVal = "${maxVal.toInt()} $unit"

        return progress.repeat(
            lengthInHalf - currentVal.length
        ) + " $currentVal $separator $maxVal " + progress.repeat(
            lengthInHalf - maxVal.length
        )
    }

    fun withGradient(borderLeft: Char, borderRight: Char): Component {
        val ratio = currentVal / maxVal
        val cutted = cutString(this.toString(), ratio)

        return DumoCore.minimsg
            .deserialize("<gray>$borderLeft<transition:#FF5555:#FFFF55:#55FF55:$ratio>${cutted.first}</transition>${cutted.second}$borderRight")
    }

    private fun cutString(str: String, ratio: Float): Pair<String, String> {
        val mid = (str.length * ratio).toInt()
        return Pair(
            str.slice(0 until mid), str.slice(mid until str.length)
        )
    }

}
