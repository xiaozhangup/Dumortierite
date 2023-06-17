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
import io.sn.dumortierite.abstracts.BioMassGenerator
import io.sn.dumortierite.abstracts.BlackMetalCoalGenerator
import io.sn.dumortierite.abstracts.CoalGenerator
import io.sn.dumortierite.objects.Circuit
import io.sn.dumortierite.utils.DHeadTexture
import io.sn.dumortierite.utils.ItemEffectUtils
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
                "&9蓝线石 : Dumortierite Craft&f"), 4)

def ICIRCUIT_GENERIC = new CustomItemStack(Material.PRISMARINE_SHARD, "&f芯片", "", "&f内部程序: &f空程序")

def ICIRCUIT_BASIC = new SlimefunItemStack("DUMO_CIRCUIT_1", ItemEffectUtils.withTier(ICIRCUIT_GENERIC, 1))
def ICIRCUIT_NORMAL = new SlimefunItemStack("DUMO_CIRCUIT_2", ItemEffectUtils.withTier(ICIRCUIT_GENERIC, 2))
def ICIRCUIT_ADVANCED = new SlimefunItemStack("DUMO_CIRCUIT_3", ItemEffectUtils.withTier(ICIRCUIT_GENERIC, 3))
def ICIRCUIT_ENHANCED = new SlimefunItemStack("DUMO_CIRCUIT_4", ItemEffectUtils.withTier(ICIRCUIT_GENERIC, 4))
def ICIRCUIT_ULTIMATE = new SlimefunItemStack("DUMO_CIRCUIT_5", ItemEffectUtils.withTier(ICIRCUIT_GENERIC, 5))
def ICIRCUIT_END = new SlimefunItemStack("DUMO_CIRCUIT_6", ItemEffectUtils.withTier(ICIRCUIT_GENERIC, 6))

def ICOAL_GENERATOR_GENERIC = new CustomItemStack(HeadTexture.GENERATOR.asItemStack, "&c煤发电机")

def ICOAL_GENERATOR_1 = new SlimefunItemStack("DUMO_COAL_GENERATOR_1",
        ItemEffectUtils.withTier(ICOAL_GENERATOR_GENERIC, 1),
        ["",
         LoreBuilder.machine(MachineTier.AVERAGE, MachineType.GENERATOR),
         LoreBuilder.powerBuffer(64),
         LoreBuilder.powerPerSecond(12)])

def ICOAL_GENERATOR_2 = new SlimefunItemStack("DUMO_COAL_GENERATOR_2",
        ItemEffectUtils.withTier(ICOAL_GENERATOR_GENERIC, 2),
        ["",
         LoreBuilder.machine(MachineTier.MEDIUM, MachineType.GENERATOR),
         LoreBuilder.powerBuffer(128),
         LoreBuilder.powerPerSecond(16)])

def ICOAL_GENERATOR_3 = new SlimefunItemStack("DUMO_COAL_GENERATOR_3",
        ItemEffectUtils.withTier(ICOAL_GENERATOR_GENERIC, 3),
        ["",
         LoreBuilder.machine(MachineTier.GOOD, MachineType.GENERATOR),
         LoreBuilder.powerBuffer(256),
         LoreBuilder.powerPerSecond(20)])

def ICOAL_GENERATOR_4 = new SlimefunItemStack("DUMO_COAL_GENERATOR_4",
        ItemEffectUtils.withTier(ICOAL_GENERATOR_GENERIC, 4),
        ["",
         "&f使用先进的催化剂加快燃料燃烧速度",
         "",
         LoreBuilder.machine(MachineTier.ADVANCED, MachineType.GENERATOR),
         LoreBuilder.powerBuffer(256),
         LoreBuilder.powerPerSecond(40)])

def ICOAL_GENERATOR_METAL = new SlimefunItemStack("DUMO_COAL_GENERATOR_METAL",
        new CustomItemStack(DHeadTexture.BLACK_METAL_GENERATOR.item, "&c合金煤发电机&r"),
        ["",
         LoreBuilder.machine(MachineTier.END_GAME, MachineType.GENERATOR),
         LoreBuilder.powerBuffer(512),
         LoreBuilder.powerPerSecond(128)])

def IBIOMASS_GENERATOR_GENERIC = new CustomItemStack(HeadTexture.GENERATOR.asItemStack, "&2生物质发电机")

def IBIOMASS_GENERATOR_1 = new SlimefunItemStack("DUMO_BIOMASS_GENERATOR_1",
        ItemEffectUtils.withTier(IBIOMASS_GENERATOR_GENERIC, 1),
        ["",
         LoreBuilder.machine(MachineTier.AVERAGE, MachineType.GENERATOR),
         LoreBuilder.powerBuffer(64),
         LoreBuilder.powerPerSecond(6)])

def IBIOMASS_GENERATOR_2 = new SlimefunItemStack("DUMO_BIOMASS_GENERATOR_2",
        ItemEffectUtils.withTier(IBIOMASS_GENERATOR_GENERIC, 2),
        ["",
         LoreBuilder.machine(MachineTier.MEDIUM, MachineType.GENERATOR),
         LoreBuilder.powerBuffer(128),
         LoreBuilder.powerPerSecond(8)])

def IBIOMASS_GENERATOR_3 = new SlimefunItemStack("DUMO_BIOMASS_GENERATOR_3",
        ItemEffectUtils.withTier(IBIOMASS_GENERATOR_GENERIC, 3),
        ["",
         LoreBuilder.machine(MachineTier.GOOD, MachineType.GENERATOR),
         LoreBuilder.powerBuffer(256),
         LoreBuilder.powerPerSecond(10)])

def IBIOMASS_GENERATOR_4 = new SlimefunItemStack("DUMO_BIOMASS_GENERATOR_4",
        ItemEffectUtils.withTier(IBIOMASS_GENERATOR_GENERIC, 4),
        ["",
         "&f加入了生物酶反应体系提高发电效率",
         "",
         LoreBuilder.machine(MachineTier.ADVANCED, MachineType.GENERATOR),
         LoreBuilder.powerBuffer(256),
         LoreBuilder.powerPerSecond(25)])
/*
  --- Miscellaneous ---
  MISCELLANEOUS REGISTER BEGIN

     __  __  _               _  _
    |  \/  |(_) ___ __  ___ | || | __ _  _ _   ___  ___  _  _  ___
    | |\/| || |(_-// _|/ -_)| || |/ _` || ' \ / -_)/ _ \| || |(_-/
    |_|  |_||_|/__/\__|\___||_||_|\__/_||_||_|\___|\___/ \_._|/__/
 */

(new Circuit(group, ICIRCUIT_BASIC, type, nullRecipe, 1).register(plug))
(new Circuit(group, ICIRCUIT_NORMAL, type, nullRecipe, 2).register(plug))
(new Circuit(group, ICIRCUIT_ADVANCED, type, nullRecipe, 3).register(plug))
(new Circuit(group, ICIRCUIT_ENHANCED, type, nullRecipe, 4).register(plug))
(new Circuit(group, ICIRCUIT_ULTIMATE, type, nullRecipe, 5).register(plug))
(new Circuit(group, ICIRCUIT_END, type, nullRecipe, 6).register(plug))

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
        .setEnergyProduction(12).register(plug))

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
        .setEnergyProduction(20).register(plug))

(new CoalGenerator(group, ICOAL_GENERATOR_4, type, nullRecipe) {
    @Override
    ItemStack getProgressBar() {
        return ItemEffectUtils.glow(new ItemStack(Material.FLINT_AND_STEEL))
    }

    @Override
    protected void registerDefaultFuelTypes() {
        registerFuel(new MachineFuel(6, new ItemStack(Material.COAL)))
        registerFuel(new MachineFuel(60, new ItemStack(Material.COAL_BLOCK)))
        registerFuel(new MachineFuel(9, new ItemStack(Material.BLAZE_ROD)))
        registerFuel(new MachineFuel(15, new ItemStack(Material.DRIED_KELP_BLOCK)))
    }
}
        .setCapacity(256)
        .setEnergyProduction(40).register(plug))

(new BlackMetalCoalGenerator(group, ICOAL_GENERATOR_METAL, type, nullRecipe) {
    @Override
    ItemStack getProgressBar() {
        return ItemEffectUtils.glow(new ItemStack(Material.FLINT_AND_STEEL))
    }

    @Override
    protected void registerDefaultFuelTypes() {
        registerFuel(new MachineFuel(18, new ItemStack(Material.COAL_BLOCK)))
        registerFuel(new MachineFuel(4, new ItemStack(Material.BLAZE_ROD)))
    }
}
        .setCapacity(512)
        .setEnergyProduction(128).register(plug))

(new BioMassGenerator(group, IBIOMASS_GENERATOR_1, type, nullRecipe) {
    @Override
    ItemStack getProgressBar() {
        return new ItemStack(Material.SLIME_BALL)
    }

    @Override
    protected void registerDefaultFuelTypes() {
        registerFuel(new MachineFuel(2, new ItemStack(Material.ROTTEN_FLESH)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.SPIDER_EYE)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.BONE)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.STRING)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.APPLE)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.MELON_SLICE)))
        registerFuel(new MachineFuel(27, new ItemStack(Material.MELON)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.PUMPKIN)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.PUMPKIN_SEEDS)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.MELON_SEEDS)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.WHEAT)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.WHEAT_SEEDS)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.CARROT)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.POTATO)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.SUGAR_CANE)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.NETHER_WART)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.RED_MUSHROOM)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.BROWN_MUSHROOM)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.VINE)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.CACTUS)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.LILY_PAD)))
        registerFuel(new MachineFuel(8, new ItemStack(Material.CHORUS_FRUIT)))
        registerFuel(new MachineFuel(1, new ItemStack(Material.KELP)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.DRIED_KELP)))
        registerFuel(new MachineFuel(20, new ItemStack(Material.DRIED_KELP_BLOCK)))
        registerFuel(new MachineFuel(1, new ItemStack(Material.SEAGRASS)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.SEA_PICKLE)))
        registerFuel(new MachineFuel(1, new ItemStack(Material.BAMBOO)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.SWEET_BERRIES)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.COCOA_BEANS)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.BEETROOT)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.BEETROOT_SEEDS)))
    }
}
        .setCapacity(64)
        .setEnergyProduction(6).register(plug))

(new BioMassGenerator(group, IBIOMASS_GENERATOR_2, type, nullRecipe) {
    @Override
    ItemStack getProgressBar() {
        return new ItemStack(Material.SLIME_BALL)
    }

    @Override
    protected void registerDefaultFuelTypes() {
        registerFuel(new MachineFuel(2, new ItemStack(Material.ROTTEN_FLESH)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.SPIDER_EYE)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.BONE)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.STRING)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.APPLE)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.MELON_SLICE)))
        registerFuel(new MachineFuel(27, new ItemStack(Material.MELON)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.PUMPKIN)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.PUMPKIN_SEEDS)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.MELON_SEEDS)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.WHEAT)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.WHEAT_SEEDS)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.CARROT)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.POTATO)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.SUGAR_CANE)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.NETHER_WART)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.RED_MUSHROOM)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.BROWN_MUSHROOM)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.VINE)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.CACTUS)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.LILY_PAD)))
        registerFuel(new MachineFuel(8, new ItemStack(Material.CHORUS_FRUIT)))
        registerFuel(new MachineFuel(1, new ItemStack(Material.KELP)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.DRIED_KELP)))
        registerFuel(new MachineFuel(20, new ItemStack(Material.DRIED_KELP_BLOCK)))
        registerFuel(new MachineFuel(1, new ItemStack(Material.SEAGRASS)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.SEA_PICKLE)))
        registerFuel(new MachineFuel(1, new ItemStack(Material.BAMBOO)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.SWEET_BERRIES)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.COCOA_BEANS)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.BEETROOT)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.BEETROOT_SEEDS)))
    }
}
        .setCapacity(128)
        .setEnergyProduction(8).register(plug))

(new BioMassGenerator(group, IBIOMASS_GENERATOR_3, type, nullRecipe) {
    @Override
    ItemStack getProgressBar() {
        return new ItemStack(Material.SLIME_BALL)
    }

    @Override
    protected void registerDefaultFuelTypes() {
        registerFuel(new MachineFuel(2, new ItemStack(Material.ROTTEN_FLESH)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.SPIDER_EYE)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.BONE)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.STRING)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.APPLE)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.MELON_SLICE)))
        registerFuel(new MachineFuel(27, new ItemStack(Material.MELON)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.PUMPKIN)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.PUMPKIN_SEEDS)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.MELON_SEEDS)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.WHEAT)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.WHEAT_SEEDS)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.CARROT)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.POTATO)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.SUGAR_CANE)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.NETHER_WART)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.RED_MUSHROOM)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.BROWN_MUSHROOM)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.VINE)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.CACTUS)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.LILY_PAD)))
        registerFuel(new MachineFuel(8, new ItemStack(Material.CHORUS_FRUIT)))
        registerFuel(new MachineFuel(1, new ItemStack(Material.KELP)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.DRIED_KELP)))
        registerFuel(new MachineFuel(20, new ItemStack(Material.DRIED_KELP_BLOCK)))
        registerFuel(new MachineFuel(1, new ItemStack(Material.SEAGRASS)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.SEA_PICKLE)))
        registerFuel(new MachineFuel(1, new ItemStack(Material.BAMBOO)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.SWEET_BERRIES)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.COCOA_BEANS)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.BEETROOT)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.BEETROOT_SEEDS)))
    }
}
        .setCapacity(256)
        .setEnergyProduction(10).register(plug))

(new BioMassGenerator(group, IBIOMASS_GENERATOR_4, type, nullRecipe) {
    @Override
    ItemStack getProgressBar() {
        return ItemEffectUtils.glow(new ItemStack(Material.SLIME_BALL))
    }

    @Override
    protected void registerDefaultFuelTypes() {
        registerFuel(new MachineFuel(2, new ItemStack(Material.ROTTEN_FLESH)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.SPIDER_EYE)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.BONE)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.STRING)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.APPLE)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.MELON_SLICE)))
        registerFuel(new MachineFuel(27, new ItemStack(Material.MELON)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.PUMPKIN)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.PUMPKIN_SEEDS)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.MELON_SEEDS)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.WHEAT)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.WHEAT_SEEDS)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.CARROT)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.POTATO)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.SUGAR_CANE)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.NETHER_WART)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.RED_MUSHROOM)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.BROWN_MUSHROOM)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.VINE)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.CACTUS)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.LILY_PAD)))
        registerFuel(new MachineFuel(8, new ItemStack(Material.CHORUS_FRUIT)))
        registerFuel(new MachineFuel(1, new ItemStack(Material.KELP)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.DRIED_KELP)))
        registerFuel(new MachineFuel(20, new ItemStack(Material.DRIED_KELP_BLOCK)))
        registerFuel(new MachineFuel(1, new ItemStack(Material.SEAGRASS)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.SEA_PICKLE)))
        registerFuel(new MachineFuel(1, new ItemStack(Material.BAMBOO)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.SWEET_BERRIES)))
        registerFuel(new MachineFuel(2, new ItemStack(Material.COCOA_BEANS)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.BEETROOT)))
        registerFuel(new MachineFuel(3, new ItemStack(Material.BEETROOT_SEEDS)))
    }
}
        .setCapacity(256)
        .setEnergyProduction(25).register(plug))
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
