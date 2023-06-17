package io.sn.dumortierite

import groovy.lang.Binding
import groovy.lang.GroovyShell
import io.sn.dumortierite.registry.ProgramRegistry
import io.sn.dumortierite.setup.CommandSetup
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.InputStreamReader


@Suppress("MemberVisibilityCanBePrivate", "unused")
open class DumoCore : JavaPlugin(), DumoSlimefunAddon {

    override fun getJavaPlugin(): JavaPlugin {
        return this
    }

    override fun getBugTrackerURL(): String {
        return "https://github.com/freeze-dolphin/Dumortierite/issues"
    }

    lateinit var plug: DumoCore

    companion object {
        var minimsg: MiniMessage = MiniMessage.miniMessage()
        var config: FileConfiguration = DumoCore.config
        var programRegistry: ProgramRegistry = ProgramRegistry()
    }

    override fun onEnable() {
        plug = this
        programRegistry.init(this)

        Thread.currentThread().contextClassLoader = this.classLoader

        setupResource()
        setupSlimefun()
        setupCommand()
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
                evaluate(getResource("slimefun_setup.groovy")?.let {
                    InputStreamReader(it)
                })
            }
        } catch (e: Exception) {
            Exception("Unexcepted error occurred while loading 'slimefun_setup.groovy'!\n${e.message}").printStackTrace()
            server.pluginManager.disablePlugin(this)
        }
    }

    fun setupPrograms(registry: ProgramRegistry) {
        try {
            with(
                GroovyShell(this.classLoader, Binding().apply {
                    setProperty("core", plug)
                    setProperty("registry", registry)
                })
            ) {
                evaluate(getResource("program_setup.groovy")?.let {
                    InputStreamReader(it)
                })
            }
        } catch (e: Exception) {
            Exception("Unexcepted error occurred while loading 'program_setup.groovy'!\n${e.message}").printStackTrace()
            server.pluginManager.disablePlugin(this)
        }
    }

    private fun setupCommand() {
        CommandSetup().init()
    }

}
