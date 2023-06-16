package io.sn.dumortierite.utils

import io.sn.dumortierite.DumoCore
import net.kyori.adventure.text.Component
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

object ItemEffectUtils {

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
            displayName(itemMeta.displayName()?.append(DumoCore.minimsg.deserialize(explainTier(tier, "<gray> - "))))
        }
        return this
    }

    @JvmStatic
    fun explainTier(tier: Int, before: String): String = when (tier) {
        1 -> {
            before + "<dark_gray>Mk.<color:#ffff55>" + RomanUtils.intToRoman(1)
        }

        2 -> {
            before + "<dark_gray>Mk.<color:#aaffaa>" + RomanUtils.intToRoman(2)
        }

        3 -> {
            before + "<dark_gray>Mk.<color:#55ffff>" + RomanUtils.intToRoman(3)
        }

        4 -> {
            before + "<dark_gray>Mk.<color:#aaaaff>" + RomanUtils.intToRoman(4)
        }

        5 -> {
            before + "<dark_gray>Mk.<color:#ff55ff>" + RomanUtils.intToRoman(5)
        }

        else -> {
            before + "<dark_gray>Mk.<color:#ff5555>" + RomanUtils.intToRoman(tier)
        }
    }
}