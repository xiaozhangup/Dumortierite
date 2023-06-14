package io.sn.dumortierite

import clojure.java.api.Clojure
import io.sn.dumortierite.registry.ItemStackRegistry
import io.sn.dumortierite.registry.ProgramRegistry
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.plugin.java.JavaPlugin


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
        var plug: DumoCore = DumoCore.plug
        var minimsg: MiniMessage = MiniMessage.miniMessage()
        var config: FileConfiguration = DumoCore.config
        var programRegistry: ProgramRegistry =
            ProgramRegistry()
        var itemStackRegistry: ItemStackRegistry = ItemStackRegistry()
    }

    override fun onEnable() {
        plug = this

        programRegistry.init()

        Thread.currentThread().contextClassLoader = this.classLoader

        setupResource()
        setupSlimefun()
    }

    private fun setupResource() {
        saveConfig()
    }

    private fun setupSlimefun() {
        /*
        with(ScriptEngineManager().getEngineByExtension("lisp")) {
            setBindings(createBindings().apply {
                put("core", plug)
            }, ScriptContext.ENGINE_SCOPE)
            eval(getResource("setup.lisp")?.let { InputStreamReader(it) })
        }
         */

        /*
        try {
            with(
                GroovyShell(this.classLoader, Binding().apply {
                    setProperty("core", plug)
                })
            ) {
                @Suppress("UNCHECKED_CAST")
                itemStackRegistry.init(evaluate(getResource("setup.groovy")?.let {
                    InputStreamReader(
                        it
                    )
                }) as ArrayList<ItemStack>)
            }
        } catch (e: Exception) {
            Exception("Unexcepted error occurred while loading 'setup.groovy'!\n${e.message}").printStackTrace()
            server.pluginManager.disablePlugin(this)
        }
         */
        Clojure.`var`("clojure.core", "require").invoke(Clojure.read("io.sn.dumortierite.clj_module.setup"))
        Clojure.`var`("io.sn.dumortierite.clj_module.setup", "setup").invoke(this)
    }


}
