package io.sn.dumortierite.utils

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem
import io.sn.dumortierite.DumoCore
import org.bukkit.Location
import org.jetbrains.annotations.NotNull

enum class ProcessorType {
    GENERATOR, MACHINE
}

enum class SpecificProgramType {
    GENERIC_GENERATOR, GENERIC_MACHINE, COAL_GENERATOR, COMPRESSOR, DEFAULT
}


abstract class AbstractProgram(val type: SpecificProgramType, val id: String, val displayName: String) {

    init {
        @Suppress("LeakingThis")
        DumoCore.programRegistry.registerProgram(this)
    }

    abstract fun load(type: SpecificProgramType)
}

class IncompatibleProgramTypeException(type: SpecificProgramType, sfItem: SlimefunItem) :
    Exception(message = "Unsupported program type (${type}) for ${sfItem.id}")

abstract class ProgramLoader(
    private val processorType: ProcessorType,
    private val allowedType: Array<out SpecificProgramType>,
    private val sfItem: SlimefunItem,
) {

    fun preLoad(program: AbstractProgram, l: Location, data: SlimefunBlockData) {
        if (program.type == SpecificProgramType.DEFAULT) return

        when (processorType) {
            ProcessorType.GENERATOR -> {
                if (allowedType.contains(program.type) || program.type == SpecificProgramType.GENERIC_GENERATOR) {
                    load(program, l, data)
                } else {
                    throw IncompatibleProgramTypeException(program.type, sfItem)
                }
            }

            ProcessorType.MACHINE -> {
                if (allowedType.contains(program.type) || program.type == SpecificProgramType.GENERIC_MACHINE) {
                    load(program, l, data)
                } else {
                    throw IncompatibleProgramTypeException(program.type, sfItem)
                }
            }
        }
    }

    abstract fun load(program: AbstractProgram, l: Location, data: SlimefunBlockData)

}
