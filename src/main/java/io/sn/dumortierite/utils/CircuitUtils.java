package io.sn.dumortierite.utils;

import de.tr7zw.nbtapi.NBT;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.utils.SlimefunUtils;
import io.sn.dumortierite.DumoCore;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class CircuitUtils {

    @SuppressWarnings("DataFlowIssue")
    public static boolean isCircuit(ItemStack item) {
        return SlimefunUtils.isItemSimilar(item, SlimefunItem.getById("DUMO_CIRCUIT_1").getItem(), true) ||
                SlimefunUtils.isItemSimilar(item, SlimefunItem.getById("DUMO_CIRCUIT_2").getItem(), true) ||
                SlimefunUtils.isItemSimilar(item, SlimefunItem.getById("DUMO_CIRCUIT_3").getItem(), true) ||
                SlimefunUtils.isItemSimilar(item, SlimefunItem.getById("DUMO_CIRCUIT_4").getItem(), true) ||
                SlimefunUtils.isItemSimilar(item, SlimefunItem.getById("DUMO_CIRCUIT_5").getItem(), true) ||
                SlimefunUtils.isItemSimilar(item, SlimefunItem.getById("DUMO_CIRCUIT_6").getItem(), true);
    }

    public static void burnProgramToCircuit(ItemStack circuit, AbstractProgram p) throws IncompatibleChipLevelException {
        if (NBT.get(circuit, nbt -> nbt.getInteger("chip-tier")) < p.getLeastChipTier()) {
            throw new IncompatibleChipLevelException();
        }

        var lore = Objects.requireNonNull(circuit.lore());
        lore.set(1, DumoCore.Companion.getMinimsg().deserialize("<!italic><white>内部程序: ").append(p.getDisplayName()));

        circuit.lore(lore);

        NBT.modify(circuit, (item) -> {
            item.setString("program-id", p.getId());
        });
    }

}
