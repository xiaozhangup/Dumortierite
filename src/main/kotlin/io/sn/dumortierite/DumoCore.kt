package io.sn.dumortierite

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

class DumoCore : SlimefunAddon, JavaPlugin() {

    override fun getJavaPlugin(): JavaPlugin {
        return this
    }

    override fun getBugTrackerURL(): String? {
        return "https://github.com/freeze-dolphin/Dumortierite/issues"
    }

/*
    override fun getName(): String {
        return super<JavaPlugin>.getName()
    }
*/

    override fun getLogger(): Logger {
        return getLogger()
    }

    override fun onEnable() {
        TODO()
    }

}
