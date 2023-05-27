package io.sn.dumortierite

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.NamespacedKey
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.nio.file.Files
import javax.script.ScriptContext
import javax.script.ScriptEngineManager

@Suppress("unused")
class DumoCore : JavaPlugin(), DumoSlimefunAddon {

    override fun getJavaPlugin(): JavaPlugin {
        return this
    }

    override fun getBugTrackerURL(): String {
        return "https://github.com/freeze-dolphin/Dumortierite/issues"
    }

    companion object {
        lateinit var minimsg: MiniMessage
        lateinit var plug: DumoCore
        var config: FileConfiguration = this.plug.config
    }

    override fun onEnable() {
        minimsg = MiniMessage.miniMessage()
        plug = this

        setupResource()
        setupSlimefun()
    }

    private fun setupResource() {
        saveConfig()

        val rbDir = File("${dataFolder.path}${File.separator}scripts")
        val rbfSetup = File("${dataFolder.path}${File.separator}scripts${File.separator}setup.rb")
        rbDir.mkdirs()

        if (!rbfSetup.exists()) {
            ClassLoader.getSystemClassLoader().getResourceAsStream("scripts${File.separator}setup.rb").use { input ->
                input?.let { Files.copy(it, rbfSetup.toPath()) }
            }
        }

    }

    private fun reachScript(@Suppress("SameParameterValue") name: String): String {
        return File("${dataFolder.path}${File.separator}scripts${File.separator}$name").readText(Charsets.UTF_8)
    }

    private fun setupSlimefun() {
        with(ScriptEngineManager().getEngineByName("jruby")) {
            val ctx: ScriptContext = context.apply {
                // prepare vars for setup
                setAttribute("plug", plug, ScriptContext.ENGINE_SCOPE)

                setAttribute(
                    "group", ItemGroup(
                        NamespacedKey(plug, "dumortierite"), CustomItemStack(
                            SlimefunUtils.getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODYzZWZhNmMxM2ExODBlYWE3MWNmMGM2NGE2MjM1MGM0MmIyNDA5M2U5OTU3NDkyYjI0NDk1NzgyNjgyNTZhYiJ9fX0="),
                            "&9Dumortierite&f"
                        ), 4
                    ), ScriptContext.ENGINE_SCOPE
                )

            }

            try {
                eval(reachScript("setup.rb"), ctx)
            } catch (e: Exception) {
                Exception("Unexcepted error occurred while loading 'setup.rb'!\n${e.stackTrace}").printStackTrace()
            }
        }
    }

}

