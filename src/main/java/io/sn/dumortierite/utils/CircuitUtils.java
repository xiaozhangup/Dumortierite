package io.sn.dumortierite.utils;

import de.tr7zw.nbtapi.NBT;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.sn.dumortierite.objects.Circuit;
import org.bukkit.inventory.ItemStack;

public class CircuitUtils {

    @SuppressWarnings("DataFlowIssue")
    public static boolean isCircuit(ItemStack item) {
        return SlimefunUtils.isItemSimilar(item, SlimefunItem.getById("DUMO_CIRCUIT_BASIC").getItem(), true) ||
                SlimefunUtils.isItemSimilar(item, SlimefunItem.getById("DUMO_CIRCUIT_NORMAL").getItem(), true) ||
                SlimefunUtils.isItemSimilar(item, SlimefunItem.getById("DUMO_CIRCUIT_ADVANCED").getItem(), true) ||
                SlimefunUtils.isItemSimilar(item, SlimefunItem.getById("DUMO_CIRCUIT_ENHANCED").getItem(), true) ||
                SlimefunUtils.isItemSimilar(item, SlimefunItem.getById("DUMO_CIRCUIT_ULTIMATE").getItem(), true) ||
                SlimefunUtils.isItemSimilar(item, SlimefunItem.getById("DUMO_CIRCUIT_END").getItem(), true);
    }

    public static void burnProgramToCircuit(ItemStack circuit, AbstractProgram p) throws IncompatibleChipLevelException {
        if (NBT.get(circuit, nbt -> nbt.getInteger("chip-tier")) < p.getLeastChipTier()) {
            throw new IncompatibleChipLevelException();
        }

        NBT.modify(circuit, (item) -> {
            item.setString("program-id", p.getId());
        });
    }

}
