package io.sn.dumortierite

import org.bukkit.inventory.ItemStack

class ItemStackRegistry {

    companion object {
        lateinit var itemStackRegistry: ArrayList<ItemStack>
    }

}

val chipsItemStack: ArrayList<ItemStack> =
    ItemStackRegistry.itemStackRegistry.subList(0, 5) as ArrayList<ItemStack>
