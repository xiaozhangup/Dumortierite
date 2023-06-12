//file:noinspection GrPackage

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineTier
import io.github.thebusybiscuit.slimefun4.core.attributes.MachineType
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack
import io.github.thebusybiscuit.slimefun4.utils.HeadTexture
import io.github.thebusybiscuit.slimefun4.utils.LoreBuilder
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils
import io.sn.dumortierite.DumoCore
import io.sn.dumortierite.abstracts.CoalGenerator
import me.mrCookieSlime.CSCoreLibPlugin.Configuration.Config
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineFuel
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.jetbrains.annotations.NotNull

import javax.annotation.Nonnull

ItemStack[] nullRecipe = new ItemStack[]{null, null, null, null, null, null, null, null, null}

RecipeType type = RecipeType.NULL;

DumoCore plug = core

ItemGroup group = new ItemGroup(new NamespacedKey(plug, "dumortierite"),
        new CustomItemStack(SlimefunUtils.getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWZhYzhhMGMxM2E1YTM2NzQ3NjBhOGY1ZTNkMWEyMzQwYWRlYmJjMWE1ODY1M2JlZTk1NjgzMjRhMWViMzNjYSJ9fX0="),
                "&9Dumortierite&f"), 4)

(new CoalGenerator(group,
        new SlimefunItemStack("DUMO_COAL_GENERATOR_1",
                HeadTexture.GENERATOR,
                "&c煤发电机 &7- &fMk.&eI", "",
                LoreBuilder.machine(MachineTier.AVERAGE, MachineType.GENERATOR),
                LoreBuilder.powerBuffer(64),
                LoreBuilder.powerPerSecond(16)), type, nullRecipe) {
    @Override
    ItemStack getProgressBar() {
        return new ItemStack(Material.COAL)
    }

    @Override
    protected void registerDefaultFuelTypes() {
        registerFuel(new MachineFuel(8, new ItemStack(Material.COAL)))
        registerFuel(new MachineFuel(80, new ItemStack(Material.COAL_BLOCK)));
        registerFuel(new MachineFuel(12, new ItemStack(Material.BLAZE_ROD)));
        registerFuel(new MachineFuel(20, new ItemStack(Material.DRIED_KELP_BLOCK)));
    }
}
        .setCapacity(64)
        .setEnergyProduction(8).register(plug))

