package io.sn.dumortierite.setup

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.Argument
import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.arguments.StringArgument
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import io.sn.dumortierite.DumoCore
import io.sn.dumortierite.utils.CircuitUtils
import io.sn.dumortierite.utils.IncompatibleChipLevelException

class CommandSetup {
    fun init() {
        val arguments = listOf<Argument<*>>(
            StringArgument("world").replaceSuggestions(
                ArgumentSuggestions.strings(DumoCore.programRegistry.allAvaliableId)
            )
        )

        CommandAPICommand("dumoburnprogram").withAliases("burnprogram", "bprog").withArguments(arguments)
            .executesPlayer(PlayerCommandExecutor { player, args ->
                val toBurn = args[0] as String
                val itemInHand = player.inventory.itemInMainHand

                if (!CircuitUtils.isCircuit(itemInHand)) {
                    player.sendMessage(DumoCore.minimsg.deserialize("<red>这不是一块芯片!"))
                } else {
                    val prog = DumoCore.programRegistry.getProgramById(toBurn)
                    if (prog != null) {
                        try {
                            CircuitUtils.burnProgramToCircuit(itemInHand, prog)
                        } catch (ex: IncompatibleChipLevelException) {
                            player.sendMessage(DumoCore.minimsg.deserialize("<red>烧录该程序至少需要 <white>${prog.leastChipTier} <red>级的芯片"))
                        }
                    }
                }
            }).register()

    }

}