@file:Suppress("MemberVisibilityCanBePrivate")

package io.sn.dumortierite

import groovy.lang.Binding
import groovy.lang.GroovyShell
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.io.InputStreamReader


@Suppress("unused")
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

        setupResource()
        setupSlimefun()
    }

    private fun setupResource() {
        saveConfig()
    }

    private fun setupSlimefun() {
        with(
            GroovyShell(this.classLoader, Binding().apply {
                setProperty("core", plug)
            })
        ) {
            @Suppress("UNCHECKED_CAST")
            itemStackRegistry =
                evaluate(getResource("setup.groovy")?.let { InputStreamReader(it) }) as ArrayList<ItemStack>
        }
    }


}
