//file:noinspection GrPackage

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineTier
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineType
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils
import io.sn.dumortierite.DumoCore
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack

ItemStack[] defaultRecipe = new ItemStack[]{null, null, null, null, null, null, null, null, null}

DumoCore plug = core

ItemGroup group = new ItemGroup(new NamespacedKey(plug, "dumortierite"),
        new CustomItemStack(SlimefunUtils.getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWZhYzhhMGMxM2E1YTM2NzQ3NjBhOGY1ZTNkMWEyMzQwYWRlYmJjMWE1ODY1M2JlZTk1NjgzMjRhMWViMzNjYSJ9fX0="),
                "&9Dumortierite&f"), 4)

SlimefunItemStack itemStackTest = new SlimefunItemStack("DUMO_TEST", new CustomItemStack(SlimefunUtils.getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2QwNDVjYTNlOGEyOWUxOTkzMTY1NjM2MmUxMjQ3NzYzM2E2ODljNDgwMWQ4ZTIxZjdkYTBmODBjYzU5ZTU2YiJ9fX0="),
        "&bSoulflow Engine&f", "", "&f从物品中提取灵魂流", "&f并用于发电", "", LoreBuilder.machine(MachineTier.AVERAGE, MachineType.GENERATOR), LoreBuilder.powerBuffer(128), LoreBuilder.powerPerSecond(32)))

(new SlimefunItem(group, itemStackTest, RecipeType.NULL, defaultRecipe)).register(plug)
