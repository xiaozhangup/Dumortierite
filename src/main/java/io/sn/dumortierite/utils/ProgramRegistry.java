package io.sn.dumortierite.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ProgramRegistry {

    public static final AbstractProgram DEFAULT_PROGRAM = new AbstractProgram(SpecificProgramType.DEFAULT, "DEFAULT", "&f空程序&r") {
        @Override
        public void load(@NotNull SpecificProgramType type) {
        }
    };

    private final Map<String, AbstractProgram> registry = new HashMap<>();

    @Nullable
    public AbstractProgram getProgramById(String id) {
        return this.registry.get(id);
    }

    public void registerProgram(AbstractProgram program) {
        this.registry.put(program.getId(), program);
    }

    public ProgramRegistry() {
    }


}
