package io.sn.dumortierite.utils

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.sn.dumortierite.DumoCore
import net.kyori.adventure.text.Component
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class ItemUtils {

    companion object {
        @JvmStatic
        fun ItemStack.glow(): ItemStack {
            itemMeta = itemMeta.apply {
                addEnchant(Enchantment.OXYGEN, 1, true)
                addItemFlags(ItemFlag.HIDE_ENCHANTS)
            }
            return this
        }

        @JvmStatic
        fun ItemStack.withTier(tier: Int): ItemStack {
            itemMeta = itemMeta.apply {
                displayName(itemMeta.displayName()?.append(explainTier(tier, "<gray> - ")))
            }
            return this
        }

        @JvmStatic
        fun explainTier(tier: Int, before: String): Component = DumoCore.minimsg.deserialize(
            when (tier) {
                1 -> "$before<dark_gray>Mk.<color:#ffff55>${RomanUtils.intToRoman(tier)}"
                2 -> "$before<dark_gray>Mk.<color:#aaffaa>${RomanUtils.intToRoman(tier)}"
                3 -> "$before<dark_gray>Mk.<color:#55ffff>${RomanUtils.intToRoman(tier)}"
                4 -> "$before<dark_gray>Mk.<color:#aaaaff>${RomanUtils.intToRoman(tier)}"
                5 -> "$before<dark_gray>Mk.<color:#ff55ff>${RomanUtils.intToRoman(tier)}"
                else -> "$before<dark_gray>Mk.<color:#ff5555><i><u>${RomanUtils.intToRoman(tier)}"
            }
        )
    }

}