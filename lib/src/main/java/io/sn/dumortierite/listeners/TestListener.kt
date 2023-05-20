package io.sn.dumortierite.listeners

import io.sn.dumortierite.utils.TransitionGauge
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class TestListener : Listener {

    @EventHandler
    fun onJoin(evt: PlayerJoinEvent) {
        for (i in 0..15) {
            evt.player.sendMessage(
                TransitionGauge(10, '/', "|", i.toFloat(), 15F, "J").withGradient('[', ']')
            )
        }
    }

}
