package io.sn.dumortierite.utils

import io.sn.dumortierite.DumoCore
import net.kyori.adventure.text.Component
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class ItemEffectUtils {

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
        fun explainTier(tier: Int, before: String): Component =
            DumoCore.minimsg.deserialize(MiscJavaUtils.explainTier(tier, before))
    }

}
