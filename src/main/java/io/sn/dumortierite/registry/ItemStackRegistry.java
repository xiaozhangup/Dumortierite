package io.sn.dumortierite.registry;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemStackRegistry {

    private final List<ItemStack> registry = new ArrayList<>();

    public List<ItemStack> getChipsItemStack() {
        return registry.subList(0, 5);
    }

    public void init(List<ItemStack> items) {
        this.registry.addAll(items);
    }

}
