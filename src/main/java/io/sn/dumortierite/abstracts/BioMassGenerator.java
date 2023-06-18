package io.sn.dumortierite.abstracts;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import de.tr7zw.nbtapi.NBT;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockPlaceHandler;
import io.github.thebusybiscuit.slimefun4.core.machines.MachineProcessor;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.handlers.SimpleBlockBreakHandler;
import io.github.thebusybiscuit.slimefun4.implementation.operations.FuelOperation;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.sn.dumortierite.DumoCore;
import io.sn.dumortierite.utils.*;
import io.sn.slimefun4.ChestMenuTexture;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ClickAction;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.AGenerator;
import me.mrCookieSlime.Slimefun.Objects.SlimefunItem.abstractItems.MachineFuel;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.mrCookieSlime.Slimefun.api.item_transport.ItemTransportFlow;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static io.sn.dumortierite.utils.LocationUtils.locOffset;

@SuppressWarnings("deprecation")
public abstract class BioMassGenerator extends AGenerator {

    private static final String KEY_PARTICLE_ENABLED = "enabled";

    private static final int[] BORDER = {0, 4, 8, 9, 13, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 31, 35, 36, 40, 44, 45, 46, 47, 47, 48, 49, 50, 51, 52, 53};

    private static final int[] SLOT_CIRCUIT_DISPLAY = {5, 6, 7, 14, 15, 16};

    private static final int[] SLOT_IN = {1, 2, 3, 10, 11, 12};
    private static final int[] SLOT_OUT = {28, 29, 30, 37, 38, 39};

    private static final int[] SLOT_OPTION = {32, 33, 34, 41, 42, 43};

    private static final int SLOT_INDICATOR = 20;
    private static final int SLOT_CIRCUIT = 17;

    private final ProgramLoader programLoader = new ProgramLoader(ProcessorType.GENERATOR, new SpecificProgramType[]{SpecificProgramType.BIOMASS_GENERATOR}, this);

    private final MachineProcessor<FuelOperation> processor = new MachineProcessor<>(this);

    @ParametersAreNonnullByDefault
    protected BioMassGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        processor.setProgressBar(getProgressBar());

        new BlockMenuPreset(item.getItemId(), getInventoryTitle(), 54, new ChestMenuTexture("dumortierite", "biomass_generator")) {
            @Override
            public void init() {
                constructMenu(this);
            }

            @Override
            public void newInstance(BlockMenu menu, Block b) {
                updateBlockInventory(menu, b);
            }

            @Override
            public boolean canOpen(Block b, Player p) {
                return p.hasPermission("slimefun.inventory.bypass") || Slimefun.getProtectionManager().hasPermission(p, b.getLocation(), Interaction.INTERACT_BLOCK);
            }

            @Override
            public int[] getSlotsAccessedByItemTransport(ItemTransportFlow flow) {
                if (flow == ItemTransportFlow.INSERT) {
                    return getInputSlots();
                } else {
                    return getOutputSlots();
                }
            }
        };

        addItemHandler(onBlockBreak(), onBlockPlace());
        registerDefaultFuelTypes();
    }

    @Nonnull
    protected BlockPlaceHandler onBlockPlace() {
        return new BlockPlaceHandler(false) {
            @Override
            public void onPlayerPlace(@NotNull BlockPlaceEvent e) {
                var blockData = StorageCacheUtils.getBlock(e.getBlock().getLocation());
                if (blockData != null) {
                    blockData.setData(KEY_PARTICLE_ENABLED, String.valueOf(false));
                }
            }
        };
    }

    @Nonnull
    @Override
    protected BlockBreakHandler onBlockBreak() {
        return new SimpleBlockBreakHandler() {

            @Override
            public void onBlockBreak(@Nonnull Block b) {
                BlockMenu inv = StorageCacheUtils.getMenu(b.getLocation());

                if (inv != null) {
                    inv.dropItems(b.getLocation(), getInputSlots());
                    inv.dropItems(b.getLocation(), getOutputSlots());
                    inv.dropItems(b.getLocation(), getMiscItemSlots());
                }

                processor.endOperation(b);
            }
        };
    }

    private void updateBlockInventory(BlockMenu menu, Block b) {
        var blockData = StorageCacheUtils.getBlock(b.getLocation());
        String val;
        if (blockData == null || (val = blockData.getData(KEY_PARTICLE_ENABLED)) == null || val.equals(String.valueOf(false))) {
            menu.replaceExistingItem(SLOT_OPTION[0], new CustomItemStack(Material.GUNPOWDER, "&7是否启用粒子效果: &4✘", "", "&e> 单击启用"));
            menu.addMenuClickHandler(SLOT_OPTION[0], (p, slot, item, action) -> {
                StorageCacheUtils.setData(b.getLocation(), KEY_PARTICLE_ENABLED, String.valueOf(true));
                updateBlockInventory(menu, b);
                return false;
            });
        } else {
            menu.replaceExistingItem(SLOT_OPTION[0], new CustomItemStack(Material.REDSTONE, "&7是否启用粒子效果: &2✔", "", "&e> 单击关闭"));
            menu.addMenuClickHandler(SLOT_OPTION[0], (p, slot, item, action) -> {
                StorageCacheUtils.setData(b.getLocation(), KEY_PARTICLE_ENABLED, String.valueOf(false));
                updateBlockInventory(menu, b);
                return false;
            });
        }
    }

    @Override
    public @NotNull MachineProcessor<FuelOperation> getMachineProcessor() {
        return this.processor;
    }

    private void constructMenu(BlockMenuPreset preset) {
        for (int i : BORDER) {
            preset.addItem(i, UIUtils.UI_BACKGROUND, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : SLOT_CIRCUIT_DISPLAY) {
            preset.addItem(i, UIUtils.UI_CODE, ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addMenuClickHandler(SLOT_CIRCUIT, (p, slot, item, action) -> true);

        for (int i : SLOT_OPTION) {
            preset.addItem(i, UIUtils.NO_OPTION, ChestMenuUtils.getEmptyClickHandler());
        }

        for (int i : getOutputSlots()) {
            preset.addMenuClickHandler(i, new ChestMenu.AdvancedMenuClickHandler() {

                @Override
                public boolean onClick(Player p, int slot, ItemStack cursor, ClickAction action) {
                    return false;
                }

                @Override
                public boolean onClick(InventoryClickEvent e, Player p, int slot, ItemStack cursor, ClickAction action) {
                    return cursor == null || cursor.getType() == Material.AIR;
                }
            });
        }

        preset.addItem(SLOT_INDICATOR, UIUtils.UI_BACKGROUND);

    }

    @Override
    public int getGeneratedOutput(@NotNull Location l, @NotNull SlimefunBlockData data) {
        BlockMenu inv = StorageCacheUtils.getMenu(l);
        FuelOperation operation = processor.getOperation(l);

        if (inv == null) return 0;

        var itemInSlot = inv.getItemInSlot(SLOT_CIRCUIT);
        if (inv.getItemInSlot(SLOT_CIRCUIT) == null) {
            return 0;
        }

        // circuit judgement!
        if (CircuitUtils.isCircuit(inv.getItemInSlot(SLOT_CIRCUIT))) {
            var program = NBT.get(itemInSlot, nbt -> nbt.getString("program-id"));
            try {
                if (!programLoader.preLoad(Objects.requireNonNull(DumoCore.Companion.getProgramRegistry().getProgramById(program)), l, data)) {
                    return 0;
                }
            } catch (IncompatibleProgramTypeException ex) {
                return 0;
            }
        } else {
            return 0;
        }
        var progressive = 0;

        var progressiveNullable = data.getData("progressive");
        if (progressiveNullable != null) {
            progressive = Integer.parseInt(progressiveNullable);
        }

        if (operation != null) {
            if (!operation.isFinished()) {
                processor.updateProgressBar(inv, SLOT_INDICATOR, operation);

                if (isChargeable()) {
                    int charge = getCharge(l, data);

                    if (getCapacity() - charge >= getEnergyProduction()) {
                        operation.addProgress(progressive);
                        if ("true".equals(data.getData(KEY_PARTICLE_ENABLED)))
                            l.getWorld().spawnParticle(Particle.BLOCK_CRACK, locOffset(l, 0.5, 0.5, 0.5), 10, 0.1, 0.1, 0.1, 0.001, Bukkit.createBlockData(Material.SLIME_BLOCK));
                        return getEnergyProduction() * progressive;
                    }

                }
            } else {
                ItemStack fuel = operation.getIngredient();

                if (isBucket(fuel)) {
                    inv.pushItem(new ItemStack(Material.BUCKET), getOutputSlots());
                }

                inv.replaceExistingItem(SLOT_INDICATOR, UIUtils.UI_BACKGROUND);

                processor.endOperation(l);
            }
        } else {
            Map<Integer, Integer> found = new HashMap<>();
            MachineFuel fuel = findRecipe(inv, found);

            if (fuel != null) {
                for (Map.Entry<Integer, Integer> entry : found.entrySet()) {
                    inv.consumeItem(entry.getKey(), entry.getValue());
                }

                processor.startOperation(l, new FuelOperation(fuel));
            }

        }
        return 0;
    }

    @Override
    public int[] getInputSlots() {
        return SLOT_IN;
    }

    @Override
    public int[] getOutputSlots() {
        return SLOT_OUT;
    }

    public int[] getMiscItemSlots() {
        return new int[]{SLOT_CIRCUIT};
    }


}
