package io.sn.dumortierite.utils

import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class ItemUtils {

    companion object {
        @JvmStatic
        fun glow(item: ItemStack): ItemStack {
            item.setItemMeta(item.itemMeta.apply {
                addEnchant(Enchantment.OXYGEN, 1, true)
                addItemFlags(ItemFlag.HIDE_ENCHANTS)
            })
            return item
        }
    }

}