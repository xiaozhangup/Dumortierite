package io.sn.dumortierite.utils

import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils
import org.bukkit.inventory.ItemStack

enum class DHeadTexture(private val base64: String) {

    BLACK_METAL_GENERATOR("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjlkYzQ4YmE1MzI2YTQwNzhkNzMxY2IwMDQ0MWU2MmJhOTQwMjQwMGMwZDE3NWI5YWM3MzRkMmQ1MmNmMjMyOSJ9fX0=");

    fun getItem(): ItemStack {
        return SlimefunUtils.getCustomHead(this.base64)
    }

}