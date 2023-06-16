package io.sn.dumortierite.utils

import io.sn.dumortierite.DumoCore
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
            displayName(
                this.displayName()?.let {
                    DumoCore.minimsg.deserialize("<!italic>").append(it)
                        .append(DumoCore.minimsg.deserialize(explainTier(tier, "<gray> - ")))
                }
            )
        }
        return this
    }

    @JvmStatic
    fun explainTier(tier: Int, before: String): String = "$before<reset><dark_gray>Mk.<color:#" + when (tier) {
        1 -> "ffff55>" + RomanUtils.intToRoman(1)
        2 -> "aaffaa>" + RomanUtils.intToRoman(2)
        3 -> "55ffff>" + RomanUtils.intToRoman(3)
        4 -> "aaaaff>" + RomanUtils.intToRoman(4)
        5 -> "ff55ff>" + RomanUtils.intToRoman(5)
        else -> "ff5555>" + RomanUtils.intToRoman(tier)
    }
}