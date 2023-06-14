(ns io.sn.dumortierite.clj_module.setup
  (:require [io.sn.dumortierite.clj-module.items :as items])
  (:import (io.github.thebusybiscuit.slimefun4.api.items ItemGroup)
           (io.github.thebusybiscuit.slimefun4.api.recipes RecipeType)
           (io.github.thebusybiscuit.slimefun4.implementation.items.electric.generators CoalGenerator)
           (io.github.thebusybiscuit.slimefun4.libraries.dough.items CustomItemStack)
           (io.github.thebusybiscuit.slimefun4.utils SlimefunUtils)
           (io.sn.dumortierite DumoCore)
           (org.bukkit Material NamespacedKey)
           (org.bukkit.inventory ItemStack)))

(set! *warn-on-reflection* true)

(defn im [^Material material]
  (ItemStack. material))

(defn setup [^DumoCore plug]
  (-> plug .getLogger (.warning "Setup section now begin..."))
  (let [defa-recipe (into-array ItemStack (repeat 9 nil))
        defa-type RecipeType/NULL
        group (ItemGroup. (NamespacedKey. plug "dumortierite") (CustomItemStack. (SlimefunUtils/getCustomHead "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWZhYzhhMGMxM2E1YTM2NzQ3NjBhOGY1ZTNkMWEyMzQwYWRlYmJjMWE1ODY1M2JlZTk1NjgzMjRhMWViMzNjYSJ9fX0=")
                                                                                 ["&9蓝线石 : Dumortierite Craft&f"]) 4)]
    (-> (proxy [CoalGenerator] [group (:coal-generator-1 items/fetch-items) defa-type defa-recipe]
          (getProgressBar [] (ItemStack. Material/FLINT_AND_STEEL))
          (registerDefaultFuelTypes [] (for [fuel [[8 (im Material/COAL)]
                                                   [80 (im Material/COAL_BLOCK)]
                                                   [12 (im Material/BLAZE_ROD)]
                                                   [20 (im Material/DRIED_KELP_BLOCK)]]]
                                         (.registerFuel (first fuel) (second fuel)))))
        (.setCapacity 128)
        (.setEnergyProduction 16)
        (.register plug))
    ;(-> (CoalGenerator. group items/DUMO_COAL_GENERATOR_1 default-type default-recipe))
    ))
