package io.sn.dumortierite.registry;

import io.sn.dumortierite.DumoCore;
import io.sn.dumortierite.utils.AbstractProgram;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ProgramRegistry {

    private final List<AbstractProgram> registry = new ArrayList<>();
    private final List<String> idRegistry = new ArrayList<>();

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
        idRegistry.add(program.getId());
    }

    public List<String> getAllAvaliableId() {
        return this.idRegistry;
    }

    public void init(DumoCore plug) {
        plug.setupPrograms(this);
    }

}
