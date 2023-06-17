package io.sn.dumortierite.abstracts;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import de.tr7zw.nbtapi.NBT;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.handlers.BlockBreakHandler;
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
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("deprecation")
public abstract class BioMassGenerator extends AGenerator {

    private static final int[] BORDER = {0, 4, 8, 9, 13, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 31, 35, 36, 40, 44, 45, 46, 47, 47, 48, 49, 50, 51, 52, 53};

    private static final int[] SLOT_CIRCUIT_DISPLAY = {5, 6, 7, 14, 15, 16};

    private static final int[] SLOT_IN = {1, 2, 3, 10, 11, 12};
    private static final int[] SLOT_OUT = {28, 29, 30, 37, 38, 39};

    private static final int[] SLOT_OPTION = {32, 33, 34, 41, 42, 43};

    private static final int SLOT_INDICATOR = 20;
    private static final int SLOT_CIRCUIT = 17;

    private final ProgramLoader programLoader = new ProgramLoader(ProcessorType.GENERATOR, new SpecificProgramType[]{SpecificProgramType.BIOMASS_GENERATOR, SpecificProgramType.BIOMASS_GENERATOR_ADV}, this);

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

        addItemHandler(onBlockBreak());
        registerDefaultFuelTypes();
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

    @Override
    public @NotNull MachineProcessor<FuelOperation> getMachineProcessor() {
        return this.processor;
    }

    private void constructMenu(BlockMenuPreset preset) {
        for (int i : BORDER) {
            preset.addItem(i, UIUtils.UI_BACKGROUND, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : SLOT_CIRCUIT_DISPLAY) {
            preset.addItem(i, UIUtils.UI_BACKGROUND, ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addMenuClickHandler(SLOT_CIRCUIT, (p, slot, item, action) -> true);

        for (int i : SLOT_OPTION) {
            if (i == SLOT_OPTION[0]) {
                preset.addItem(i, new CustomItemStack(Material.GUNPOWDER, "&f粒子效果 (加工时): &c关闭"), new ChestMenu.AdvancedMenuClickHandler() {
                    @Override
                    public boolean onClick(InventoryClickEvent e, Player p, int slot, ItemStack cursor, ClickAction action) {
                        var loc = Objects.requireNonNull(e.getClickedInventory()).getLocation();
                        if (loc != null) {
                            var data = StorageCacheUtils.getBlock(loc);
                            if (data != null) {
                                if (Boolean.parseBoolean(data.getData("switch-particle"))) {
                                    data.setData("switch-particle", "true");
                                    var im = Objects.requireNonNull(e.getCurrentItem()).getItemMeta();
                                    im.displayName(DumoCore.Companion.getMinimsg().deserialize("<white>粒子效果 (加工时): <green>启用"));
                                    e.getCurrentItem().setItemMeta(im);
                                    e.getCurrentItem().setType(Material.REDSTONE);
                                } else {
                                    data.setData("switch-particle", "false");
                                    var im = Objects.requireNonNull(e.getCurrentItem()).getItemMeta();
                                    im.displayName(DumoCore.Companion.getMinimsg().deserialize("<white>粒子效果 (加工时): <red>关闭"));
                                    e.getCurrentItem().setItemMeta(im);
                                }
                            }
                        }
                        return false;
                    }

                    @Override
                    public boolean onClick(Player p, int slot, ItemStack item, ClickAction action) {
                        return false;
                    }
                });
            } else
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
        if (data.getData("switch-particle") == null) {
            data.setData("switch-particle", "false");
        }

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
                        if (Boolean.parseBoolean(data.getData("switch-particle"))) {
                            l.getWorld().spawnParticle(Particle.SLIME, l, 10, 0.5, 1.2, 0.5);
                        }
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
