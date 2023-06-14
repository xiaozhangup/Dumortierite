package io.sn.dumortierite.registry;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import io.sn.dumortierite.utils.AbstractProgram;
import io.sn.dumortierite.utils.SpecificProgramType;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ProgramRegistry {

    private final List<AbstractProgram> registry = new ArrayList<>();

    @Nullable
    public AbstractProgram getProgramById(String id) {
        for (AbstractProgram ap : this.registry) {
            if (ap.getId().equals(id)) {
                return ap;
            }
        }
        return null;
    }

    public void registerProgram(AbstractProgram program) {
        registry.add(program);
    }

    public void init() {
        registerProgram(new AbstractProgram(SpecificProgramType.DEFAULT, "&f空程序&r", 0) {
            @Override
            public void load(@NotNull AbstractProgram program, @NotNull Location l, @NotNull SlimefunBlockData data) {
                l.getWorld().spawnParticle(Particle.END_ROD, 0.5, 0.5, 0.5, 5);
            }
        });
    }

}
