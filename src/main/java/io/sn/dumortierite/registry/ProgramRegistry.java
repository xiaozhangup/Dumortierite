package io.sn.dumortierite.registry;

import io.sn.dumortierite.DumoCore;
import io.sn.dumortierite.utils.AbstractProgram;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProgramRegistry {

    private final Map<String, AbstractProgram> registry = new HashMap<>();
    private final List<String> idRegistry = new ArrayList<>();

    @Nullable
    public AbstractProgram getProgramById(String id) {
        for (String s : registry.keySet()) {
            if (s.equals(id)) {
                return registry.get(s);
            }
        }
        return null;
    }

    public void registerProgram(String id, AbstractProgram program) {
        registry.put(id, program);
        idRegistry.add(id);
    }

    public List<String> getAllAvaliableId() {
        return this.idRegistry;
    }

    public void init(DumoCore plug) {
        plug.setupPrograms(this);
    }

}
