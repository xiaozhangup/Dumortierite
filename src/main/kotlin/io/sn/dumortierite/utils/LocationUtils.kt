package io.sn.dumortierite.utils

import org.bukkit.Location

object LocationUtils {

    @JvmStatic
    fun Location.locOffset(x: Double, y: Double, z: Double): Location {
        val loc = this.clone()
        return Location(loc.world, loc.x + x, loc.y + y, loc.z + z)
    }

}