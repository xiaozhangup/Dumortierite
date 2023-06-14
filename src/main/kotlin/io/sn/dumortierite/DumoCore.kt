package io.sn.dumortierite

import groovy.lang.Binding
import groovy.lang.GroovyObject
import groovy.lang.GroovyShell
import io.sn.dumortierite.utils.ProgramRegistry
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.io.InputStreamReader

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
        var programRegistry: ProgramRegistry = ProgramRegistry()
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
        try {
            with(
                GroovyShell(this.classLoader, Binding().apply {
                    setProperty("core", plug)
                })
            ) {
                @Suppress("UNCHECKED_CAST")
                ItemStackRegistry.itemStackRegistry.addAll(evaluate(getResource("setup.groovy")?.let {
                    InputStreamReader(
                        it
                    )
                }) as ArrayList<ItemStack>)
            }
        } catch (e: Exception) {
            Exception("Unexcepted error occurred while loading 'setup.groovy'!\n${e.message}").printStackTrace()
            server.pluginManager.disablePlugin(this)
        }
    }


}
