package io.sn.dumortierite.utils

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem
import io.sn.dumortierite.DumoCore
import net.kyori.adventure.text.Component
import org.bukkit.Location

enum class ProcessorType {
    GENERATOR, MACHINE
}

enum class SpecificProgramType {
    DEFAULT, GENERIC_GENERATOR, GENERIC_MACHINE,
    COAL_GENERATOR, COAL_GENERATOR_ADV, BLACK_METAL_COAL_GENERATOR,
    BIOMASS_GENERATOR, BIOMASS_GENERATOR_ADV,
    COMPRESSOR,
}


abstract class AbstractProgram(
    val type: SpecificProgramType,
    val leastChipTier: Int,
    private val name: String,
    private val desc: String
) {
    val displayName: Component
        get() = DumoCore.minimsg.deserialize(name)

    val displayDesc: Component
        get() = DumoCore.minimsg.deserialize(desc)

    val id: String = type.name

    abstract fun load(
        program: AbstractProgram,
        l: Location,
        data: SlimefunBlockData
    )
}

class IncompatibleProgramTypeException(type: SpecificProgramType, sfItem: SlimefunItem) :
    Exception("Unsupported program type (${type}) for ${sfItem.id}")

class IncompatibleChipLevelException() :
    Exception("This program needs more advanced chip to burn on.")

class ProgramLoader(
    private val processorType: ProcessorType,
    private val allowedType: Array<out SpecificProgramType>,
    private val sfItem: SlimefunItem,
) {

    @Throws(IncompatibleProgramTypeException::class)
    fun preLoad(program: AbstractProgram, l: Location, data: SlimefunBlockData): Boolean {
        if (program.type == SpecificProgramType.DEFAULT) return false

        when (processorType) {
            ProcessorType.GENERATOR -> {
                if (allowedType.contains(program.type) || program.type == SpecificProgramType.GENERIC_GENERATOR) {
                    program.load(program, l, data)
                    return true
                } else {
                    throw IncompatibleProgramTypeException(program.type, sfItem)
                }
            }

            ProcessorType.MACHINE -> {
                if (allowedType.contains(program.type) || program.type == SpecificProgramType.GENERIC_MACHINE) {
                    program.load(program, l, data)
                    return true
                } else {
                    throw IncompatibleProgramTypeException(program.type, sfItem)
                }
            }
        }
    }
}
