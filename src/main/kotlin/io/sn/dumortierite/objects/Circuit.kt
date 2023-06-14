package io.sn.dumortierite.objects

import de.tr7zw.nbtapi.NBT
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun4.core.handlers.ItemUseHandler
import io.sn.dumortierite.DumoCore
import io.sn.dumortierite.utils.AbstractProgram
import io.sn.dumortierite.utils.ProgramRegistry
import io.sn.dumortierite.utils.ItemUtils.Companion.explainTier
import org.bukkit.inventory.ItemStack

class Circuit(
    itemGroup: ItemGroup,
    item: SlimefunItemStack,
    recipeType: RecipeType,
    recipe: Array<out ItemStack>,
    private val tier: Int
) : SlimefunItem(itemGroup, item, recipeType, recipe) {

    private fun setProgram(p: AbstractProgram) {
        NBT.modify(item) {
            it.setString("program-id", p.id)
            it.setInteger("chip-tier", tier)
        }
    }

    override fun preRegister() {
        setProgram(ProgramRegistry.DEFAULT_PROGRAM)
        addItemHandler(ItemUseHandler {
            val id = NBT.get(item) {
                it.getString("program-id")
            }
            val tier = NBT.get(item) {
                it.getInteger("chip-tier")
            }

            it.player.sendMessage(DumoCore.minimsg.deserialize("<white>芯片级别: ${explainTier(tier, "")}"))
            it.player.sendMessage(
                DumoCore.minimsg.deserialize(
                    "<white>内部程序: ${
                        DumoCore.programRegistry.getProgramById(
                            id
                        )?.displayName
                    }"
                )
            )
        })
    }

}