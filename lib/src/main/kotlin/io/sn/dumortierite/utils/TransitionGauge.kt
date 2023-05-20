package io.sn.dumortierite.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage

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
        return MiniMessage.miniMessage()
            .deserialize("<gray>$borderLeft<transition:#FF5555:#FFFF55:#55FF55:${currentVal / maxVal}>$this</transition>$borderRight")
    }

}
