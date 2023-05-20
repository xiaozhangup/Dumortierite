package io.sn.dumortierite.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage

open class Gauge(
    private val lengthInHalf: Int,
    private val separator: Char,
    private val progress: CharSequence,
    private val currentVal: String,
    private val maxVal: String,
) {

    override fun toString(): String {
        return progress.repeat(
            lengthInHalf - currentVal.length
        ) + " $currentVal $separator $maxVal " + progress.repeat(
            lengthInHalf - maxVal.length
        )
    }

    fun withGradient(borderLeft: Char, borderRight: Char): Component {
        return MiniMessage.miniMessage()
            .deserialize("<gray>$borderLeft<gradient:#FF5555:#FFFF55:#55FF55>$this</gradient>$borderRight")
    }

}
