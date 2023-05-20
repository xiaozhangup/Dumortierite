package io.sn.dumortierite

import org.bukkit.plugin.java.JavaPlugin

class DumoCore : JavaPlugin(), DumoSlimefunAddon {

    override fun getJavaPlugin(): JavaPlugin {
        return this
    }

    override fun getBugTrackerURL(): String {
        return "https://github.com/freeze-dolphin/Dumortierite/issues"
    }


    override fun onEnable() {
        TODO()
    }

}
