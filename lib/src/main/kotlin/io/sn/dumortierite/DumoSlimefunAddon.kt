package io.sn.dumortierite

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon
import java.util.logging.Logger

interface DumoSlimefunAddon : SlimefunAddon {

    override fun getName(): String

    override fun getLogger(): Logger
}
