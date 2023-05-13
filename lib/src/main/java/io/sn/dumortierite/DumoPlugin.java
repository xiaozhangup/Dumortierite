package io.sn.dumortierite;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DumoPlugin extends JavaPlugin implements SlimefunAddon {

    static {
        Thread.currentThread().setContextClassLoader(DumoPlugin.class.getClassLoader());
    }

    @NotNull
    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Nullable
    @Override
    public String getBugTrackerURL() {
        return "https://github.com/freeze-dolphin/Dumortierite/issues";
    }

}
