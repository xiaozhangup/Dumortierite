package io.sn.dumortierite.abstracts;

import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.util.StorageCacheUtils;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.machines.MachineProcessor;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.operations.FuelOperation;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.protection.Interaction;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.sn.dumortierite.utils.ConsUtils;
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
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("deprecation")
public abstract class CoalGenerator extends AGenerator {

    private static final int[] BORDER = {0, 4, 8, 9, 13, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 31, 35, 36, 40, 44, 45, 46, 47, 47, 48, 49, 50, 51, 52, 53};

    private static final int[] SLOT_CIRCUIT_DISPLAY = {5, 6, 7, 14, 15, 16};

    private static final int[] SLOT_IN = {1, 2, 3, 10, 11, 12};
    private static final int[] SLOT_OUT = {28, 29, 30, 37, 38, 39};
    private static final int[] SLOT_OPTION = {32, 33, 34, 41, 42, 43};

    private static final int SLOT_INDICATOR = 20;
    private static final int SLOT_CIRCUIT = 17;

    private final MachineProcessor<FuelOperation> processor = new MachineProcessor<>(this);

    @ParametersAreNonnullByDefault
    protected CoalGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);

        processor.setProgressBar(getProgressBar());

        new BlockMenuPreset(item.getItemId(), getInventoryTitle(), new ChestMenuTexture("dumortierite", "coal_generator")) {
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

    @Override
    public @NotNull MachineProcessor<FuelOperation> getMachineProcessor() {
        return this.processor;
    }

    private void constructMenu(BlockMenuPreset preset) {
        for (int i : BORDER) {
            preset.addItem(i, ConsUtils.EMPTY_PLACEHOLDER, ChestMenuUtils.getEmptyClickHandler());
        }
        for (int i : SLOT_CIRCUIT_DISPLAY) {
            preset.addItem(i, ConsUtils.EMPTY_PLACEHOLDER, ChestMenuUtils.getEmptyClickHandler());
        }
        preset.addMenuClickHandler(SLOT_CIRCUIT, (p, slot, item, action) -> {
            return false; //TODO replace with circuit judgement!
        });

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

        preset.addItem(SLOT_INDICATOR, new CustomItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " "), ChestMenuUtils.getEmptyClickHandler());

    }

    @Override
    public int getGeneratedOutput(@NotNull Location l, @NotNull SlimefunBlockData data) {
        BlockMenu inv = StorageCacheUtils.getMenu(l);
        FuelOperation operation = processor.getOperation(l);

        if (inv == null) return 0;

        if (operation != null) {
            if (!operation.isFinished()) {
                processor.updateProgressBar(inv, SLOT_INDICATOR, operation);

                if (isChargeable()) {
                    int charge = getCharge(l, data);

                    if (getCapacity() - charge >= getEnergyProduction()) {
                        operation.addProgress(1);
                        return getEnergyProduction();
                    }

                    return 0;
                } else {
                    operation.addProgress(1);
                    return getEnergyProduction();
                }
            } else {
                ItemStack fuel = operation.getIngredient();

                if (isBucket(fuel)) {
                    inv.pushItem(new ItemStack(Material.BUCKET), getOutputSlots());
                }

                inv.replaceExistingItem(SLOT_INDICATOR, new CustomItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " "));

                processor.endOperation(l);
                return 0;
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

            return 0;
        }
    }

    @Override
    public int[] getInputSlots() {
        return SLOT_IN;
    }

    @Override
    public int[] getOutputSlots() {
        return SLOT_OUT;
    }


}
