package io.sn.dumortierite.utils

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem
import org.bukkit.ChatColor
import org.bukkit.Location

enum class ProcessorType {
    GENERATOR, MACHINE
}

enum class SpecificProgramType {
    GENERIC_GENERATOR, GENERIC_MACHINE, COAL_GENERATOR, COMPRESSOR, DEFAULT
}


abstract class AbstractProgram(
    val type: SpecificProgramType,
    private val name: String,
    val leastChipTier: Int,
) {
    val displayName: String
        get() = ChatColor.translateAlternateColorCodes('&', this.type.name)

    val id: String = type.name

    abstract fun load(
        program: AbstractProgram,
        l: Location,
        data: SlimefunBlockData
    )
}

class IncompatibleProgramTypeException(type: SpecificProgramType, sfItem: SlimefunItem) :
    Exception("Unsupported program type (${type}) for ${sfItem.id}")

class ProgramLoader(
    private val processorType: ProcessorType,
    private val allowedType: Array<out SpecificProgramType>,
    private val sfItem: SlimefunItem,
) {

    fun preLoad(program: AbstractProgram, l: Location, data: SlimefunBlockData) {
        if (program.type == SpecificProgramType.DEFAULT) return

        when (processorType) {
            ProcessorType.GENERATOR -> {
                if (allowedType.contains(program.type) || program.type == SpecificProgramType.GENERIC_GENERATOR) {
                    program.load(program, l, data)
                } else {
                    throw IncompatibleProgramTypeException(program.type, sfItem)
                }
            }

            ProcessorType.MACHINE -> {
                if (allowedType.contains(program.type) || program.type == SpecificProgramType.GENERIC_MACHINE) {
                    program.load(program, l, data)
                } else {
                    throw IncompatibleProgramTypeException(program.type, sfItem)
                }
            }
        }
    }
}
