@file:Suppress("MemberVisibilityCanBePrivate")

package io.sn.dumortierite

import groovy.lang.Binding
import groovy.lang.GroovyShell
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File


@Suppress("unused")
class DumoCore : JavaPlugin(), DumoSlimefunAddon {

    override fun getJavaPlugin(): JavaPlugin {
        return this
    }

    override fun getBugTrackerURL(): String {
        return "https://github.com/freeze-dolphin/Dumortierite/issues"
    }

    lateinit var plug: DumoCore
    lateinit var setupScriptFile: File

    companion object {
        var plug: DumoCore = DumoCore.plug
        var minimsg: MiniMessage = MiniMessage.miniMessage()
        var config: FileConfiguration = DumoCore.config
    }

    override fun onEnable() {
        plug = this
        setupScriptFile = File("${dataFolder.path}${File.separator}setup.groovy")

        setupResource()
        setupSlimefun()
    }

    private fun setupResource() {
        saveConfig()
        if (!setupScriptFile.exists()) saveResource("setup.groovy", false)
    }

    private fun setupSlimefun() {
        try {
            with(
                GroovyShell(this.classLoader, Binding().apply {
                    setProperty("core", plug)
                })
            ) {
                evaluate(setupScriptFile)
            }
        } catch (e: Exception) {
            Exception("Unexcepted error occurred while loading 'setup.groovy'!\n${e.message}").printStackTrace()
        }
    }


}
