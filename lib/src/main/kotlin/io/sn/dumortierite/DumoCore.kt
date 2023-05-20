package io.sn.dumortierite

import io.sn.dumortierite.listeners.TestListener
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
        server.pluginManager.registerEvents(TestListener(), this)
    }

}
