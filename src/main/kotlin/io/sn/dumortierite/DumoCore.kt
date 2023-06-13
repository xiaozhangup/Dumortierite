package io.sn.dumortierite

import clojure.java.api.Clojure
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin

@Suppress("MemberVisibilityCanBePrivate", "unused")
class DumoCore : JavaPlugin(), DumoSlimefunAddon {

    override fun getJavaPlugin(): JavaPlugin {
        return this
    }

    override fun getBugTrackerURL(): String {
        return "https://github.com/freeze-dolphin/Dumortierite/issues"
    }

    lateinit var plug: DumoCore

    companion object {
        var plug: DumoCore = DumoCore.plug
        var minimsg: MiniMessage = MiniMessage.miniMessage()
        var config: FileConfiguration = DumoCore.config
        lateinit var itemStackRegistry: ArrayList<ItemStack>
    }

    override fun onEnable() {
        plug = this

        Thread.currentThread().contextClassLoader = this.classLoader

        setupResource()
        setupSlimefun()
    }

    private fun setupResource() {
        saveConfig()
    }

    private fun setupSlimefun() {
        Clojure.`var`("clojure.core", "require").invoke(Clojure.read("io.sn.dumortierite.clj_module.setup"))
        Clojure.`var`("io.sn.dumortierite.clj_module.setup", "init").invoke(this)
    }


}
