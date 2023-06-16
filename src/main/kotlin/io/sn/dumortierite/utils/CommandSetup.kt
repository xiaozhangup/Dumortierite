package io.sn.dumortierite.utils

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.arguments.Argument
import dev.jorel.commandapi.arguments.ArgumentSuggestions
import dev.jorel.commandapi.arguments.StringArgument
import dev.jorel.commandapi.executors.PlayerCommandExecutor
import io.sn.dumortierite.DumoCore

class CommandSetup {
    fun init(plug: DumoCore) {
        val arguments = listOf<Argument<*>>(
            StringArgument("world").replaceSuggestions(
                ArgumentSuggestions.strings(DumoCore.programRegistry.allAvaliableId)
            )
        )

        CommandAPICommand("dumoburnprogram").withAliases("burnprogram", "bprog").withArguments(arguments)
            .executesPlayer(PlayerCommandExecutor { player, args ->
                val toBurn = args[0] as String
                val itemInHand = player.inventory.itemInMainHand

                if (CircuitUtils.isCircuit(itemInHand)) {
                    player.sendMessage(DumoCore.minimsg.deserialize("<red>这不是一块芯片!"))
                } else {
                    CircuitUtils.burnProgramToCircuit(itemInHand, DumoCore.programRegistry.getProgramById(toBurn))
                }
            }).register()

    }

}