package io.sn.dumortierite

import io.sn.dumortierite.utils.Gauge
import org.bukkit.plugin.java.JavaPlugin

@Suppress("unused")
class DumoCore : JavaPlugin(), DumoSlimefunAddon {

    override fun getJavaPlugin(): JavaPlugin {
        return this
    }

    override fun getBugTrackerURL(): String {
        return "https://github.com/freeze-dolphin/Dumortierite/issues"
    }
    
    override fun onEnable() {
        for (i in 0..1000) {
            server.consoleSender.sendMessage(Gauge(10, '/', "|", "$i J", "1000 J").withGradient('[', ']'))
        }
    }

}
