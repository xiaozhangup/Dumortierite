//file:noinspection GrPackage
//file:noinspection GrDeprecatedAPIUsage


import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup
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
import io.sn.dumortierite.utils.ItemUtils
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineFuel
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack

//noinspection GrUnresolvedAccess
DumoCore plug = core

// default options
def nullRecipe = new ItemStack[]{null, null, null, null, null, null, null, null, null}
def type = RecipeType.NULL

def group = new ItemGroup(new NamespacedKey(plug, "dumortierite"),
        new CustomItemStack(SlimefunUtils.getCustomHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWZhYzhhMGMxM2E1YTM2NzQ3NjBhOGY1ZTNkMWEyMzQwYWRlYmJjMWE1ODY1M2JlZTk1NjgzMjRhMWViMzNjYSJ9fX0="),
                "&9Dumortierite&f"), 4)

def ICOAL_GENERATOR_1 = new SlimefunItemStack("DUMO_COAL_GENERATOR_1",
        HeadTexture.GENERATOR,
        "&c煤发电机 &7- &fMk.&eI&r", "",
        LoreBuilder.machine(MachineTier.AVERAGE, MachineType.GENERATOR),
        LoreBuilder.powerBuffer(64),
        LoreBuilder.powerPerSecond(16))

def ICOAL_GENERATOR_2 = new SlimefunItemStack("DUMO_COAL_GENERATOR_2",
        HeadTexture.GENERATOR,
        "&c煤发电机 &7- &fMk.&eII&r", "",
        LoreBuilder.machine(MachineTier.AVERAGE, MachineType.GENERATOR),
        LoreBuilder.powerBuffer(128),
        LoreBuilder.powerPerSecond(32))

def ICOAL_GENERATOR_3 = new SlimefunItemStack("DUMO_COAL_GENERATOR_3",
        HeadTexture.GENERATOR,
        "&c煤发电机 &7- &fMk.&eIII&r", "",
        LoreBuilder.machine(MachineTier.AVERAGE, MachineType.GENERATOR),
        LoreBuilder.powerBuffer(256),
        LoreBuilder.powerPerSecond(64))

def ICOAL_GENERATOR_4 = new SlimefunItemStack("DUMO_COAL_GENERATOR_4",
        HeadTexture.GENERATOR,
        "&c煤发电机 &7- &fMk.&eIV&r", "",
        "&f使用先进的催化剂加快燃料燃烧速度", "",
        LoreBuilder.machine(MachineTier.AVERAGE, MachineType.GENERATOR),
        LoreBuilder.powerBuffer(512),
        LoreBuilder.powerPerSecond(128))

/*
  --- Miscellaneous ---
  MISCELLANEOUS REGISTER BEGIN

     __  __  _               _  _
    |  \/  |(_) ___ __  ___ | || | __ _  _ _   ___  ___  _  _  ___
    | |\/| || |(_-// _|/ -_)| || |/ _` || ' \ / -_)/ _ \| || |(_-/
    |_|  |_||_|/__/\__|\___||_||_|\__/_||_||_|\___|\___/ \_._|/__/
 */

// MISCELLANEOUS REGISTER END

/*
  --- Generators ---
  GENERATORS REGISTER BEGIN
      ___                             _
     / __| ___  _ _   ___  _ _  __ _ | |_  ___  _ _  ___
    | (_ |/ -_)| ' \ / -_)| '_|/ _` ||  _|/ _ \| '_|(_-/
     \___|\___||_||_|\___||_|  \__/_| \__|\___/|_|  /__/
 */
(new CoalGenerator(group, ICOAL_GENERATOR_1, type, nullRecipe) {
    @Override
    ItemStack getProgressBar() {
        return new ItemStack(Material.FLINT_AND_STEEL)
    }

    @Override
    protected void registerDefaultFuelTypes() {
        registerFuel(new MachineFuel(8, new ItemStack(Material.COAL)))
        registerFuel(new MachineFuel(80, new ItemStack(Material.COAL_BLOCK)))
        registerFuel(new MachineFuel(12, new ItemStack(Material.BLAZE_ROD)))
        registerFuel(new MachineFuel(20, new ItemStack(Material.DRIED_KELP_BLOCK)))
    }
}
        .setCapacity(64)
        .setEnergyProduction(8).register(plug))

(new CoalGenerator(group, ICOAL_GENERATOR_2, type, nullRecipe) {
    @Override
    ItemStack getProgressBar() {
        return new ItemStack(Material.FLINT_AND_STEEL)
    }

    @Override
    protected void registerDefaultFuelTypes() {
        registerFuel(new MachineFuel(8, new ItemStack(Material.COAL)))
        registerFuel(new MachineFuel(80, new ItemStack(Material.COAL_BLOCK)))
        registerFuel(new MachineFuel(12, new ItemStack(Material.BLAZE_ROD)))
        registerFuel(new MachineFuel(20, new ItemStack(Material.DRIED_KELP_BLOCK)))
    }
}
        .setCapacity(128)
        .setEnergyProduction(16).register(plug))

(new CoalGenerator(group, ICOAL_GENERATOR_3, type, nullRecipe) {
    @Override
    ItemStack getProgressBar() {
        return new ItemStack(Material.FLINT_AND_STEEL)
    }

    @Override
    protected void registerDefaultFuelTypes() {
        registerFuel(new MachineFuel(8, new ItemStack(Material.COAL)))
        registerFuel(new MachineFuel(80, new ItemStack(Material.COAL_BLOCK)))
        registerFuel(new MachineFuel(12, new ItemStack(Material.BLAZE_ROD)))
        registerFuel(new MachineFuel(20, new ItemStack(Material.DRIED_KELP_BLOCK)))
    }
}
        .setCapacity(256)
        .setEnergyProduction(32).register(plug))

(new CoalGenerator(group, ICOAL_GENERATOR_4, type, nullRecipe) {
    @Override
    ItemStack getProgressBar() {
        return ItemUtils.glow(new ItemStack(Material.FLINT_AND_STEEL))
    }

    @Override
    protected void registerDefaultFuelTypes() {
        registerFuel(new MachineFuel(4, new ItemStack(Material.COAL)))
        registerFuel(new MachineFuel(40, new ItemStack(Material.COAL_BLOCK)))
        registerFuel(new MachineFuel(6, new ItemStack(Material.BLAZE_ROD)))
        registerFuel(new MachineFuel(10, new ItemStack(Material.DRIED_KELP_BLOCK)))
    }
}
        .setCapacity(256)
        .setEnergyProduction(32).register(plug))

// GENERATORS REGISTER END

/*
  --- Machines ---
  MACHINES REGISTER BEGIN
     __  __            _     _
    |  \/  | __ _  __ | |_  (_) _ _   ___  ___
    | |\/| |/ _` |/ _||   \ | || ' \ / -_)(_-/
    |_|  |_|\__/_|\__||_||_||_||_||_|\___|/__/
 */

// MACHINES REGISTER END

/*
  --- Cleanup ---
 */
// throw back registry to DumoCore.kt
return [

] as ArrayList<ItemStack>
