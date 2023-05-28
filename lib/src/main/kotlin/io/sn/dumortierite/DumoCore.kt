package io.sn.dumortierite

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.nio.file.Files
import javax.script.ScriptEngineManager


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
    }

    override fun onEnable() {
        plug = this

        setupResource()
        setupSlimefun()
    }

    private fun setupResource() {
        saveConfig()

        val rbDir = File("${dataFolder.path}${File.separator}scripts")
        val rbfSetup = File("${dataFolder.path}${File.separator}scripts${File.separator}setup.groovy")
        rbDir.mkdirs()

        if (!rbfSetup.exists()) {
            ClassLoader.getSystemClassLoader().getResourceAsStream("scripts${File.separator}setup.groovy")
                .use { input ->
                    input?.let { Files.copy(it, rbfSetup.toPath()) }
                }
        }

    }

    private fun reachScript(@Suppress("SameParameterValue") name: String): String {
        return File("${dataFolder.path}${File.separator}scripts${File.separator}$name").readText(Charsets.UTF_8)
    }

    private fun setupSlimefun() {
        val sem = ScriptEngineManager()
        val factories = sem.engineFactories
        for (factory in factories) println(factory.engineName + " " + factory.engineVersion + " " + factory.names)


        with(sem.getEngineByExtension("groovy")) {
            val ctx = createBindings().apply {
                // prepare vars for setup
                put("core", plug)
            }

            try {
                eval(reachScript("setup.groovy"), ctx)
            } catch (e: Exception) {
                Exception("Unexcepted error occurred while loading 'setup.groovy'!\n${e.stackTrace}").printStackTrace()
            }
        }
    }

}

