(ns io.sn.dumortierite.clj_module.setup
  (:require [io.sn.dumortierite.clj_module.items :as items]
            [io.sn.dumortierite.clj_module.defaults :as defaults])
  (:import (io.github.thebusybiscuit.slimefun4.api.items ItemGroup SlimefunItem SlimefunItemStack)
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

(def ^{:dynamic true
       :tag     DumoCore} plug)

(def ^{:dynamic true} defa-recipe)
(def ^{:dynamic true} defa-type)
(def ^{:dynamic true} group)


(defmacro gen-fuel [fuel-list]
  `(for [fuel# ~fuel-list]
     (.registerFuel (first fuel#) (second fuel#))))

(defn register
  ([sf-item {capacity :capacity production :production}]
   (-> sf-item (.setCapacity 128) (.setEnergyProduction 16) (.register plug)))
  ([sf-item]
   (-> sf-item (.register plug))))

(defmacro default-options [^SlimefunItemStack item]
  `[group ~item defa-type defa-recipe])

(def ^{:dynamic true
       :tag     String} base)

(defmacro gen-abs-sf-item [item & more]
  `(proxy [~(symbol base)] [group ~item defa-type defa-recipe] ~@more))

(defn setup [^DumoCore instance]
  (-> instance .getLogger (.warning "Setup section now begin..."))
  (binding [plug instance
            defa-recipe (into-array ItemStack (repeat 9 nil))
            defa-type RecipeType/NULL
            group (ItemGroup. (NamespacedKey. instance "dumortierite") (CustomItemStack.
                                                                         (SlimefunUtils/getCustomHead "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWZhYzhhMGMxM2E1YTM2NzQ3NjBhOGY1ZTNkMWEyMzQwYWRlYmJjMWE1ODY1M2JlZTk1NjgzMjRhMWViMzNjYSJ9fX0=")
                                                                         ["蓝线石 : Dumortierite Craft&f"]) 4)]
    ; Circuit
    (binding [base "Circuit"]
      (doseq [level (range 1 7)]
        (register (SlimefunItem. group (items/fetch-items :circuit-1) defa-type defa-recipe))))

    ; Coal Generator
    (binding [base "CoalGenerator"]
      (doseq [each (partition 2 (interleave
                                  [(gen-abs-sf-item (items/fetch-items :coal-generator-1)
                                                    (getProgressBar [] (ItemStack. Material/FLINT_AND_STEEL))
                                                    (registerDefaultFuelTypes [] (gen-fuel (defaults/fuel-list :coal-generator))))
                                   (gen-abs-sf-item (items/fetch-items :coal-generator-2)
                                                    (getProgressBar [] (ItemStack. Material/FLINT_AND_STEEL))
                                                    (registerDefaultFuelTypes [] (gen-fuel (defaults/fuel-list :coal-generator))))
                                   (gen-abs-sf-item (items/fetch-items :coal-generator-3)
                                                    (getProgressBar [] (ItemStack. Material/FLINT_AND_STEEL))
                                                    (registerDefaultFuelTypes [] (gen-fuel (defaults/fuel-list :coal-generator))))
                                   (gen-abs-sf-item (items/fetch-items :coal-generator-4)
                                                    (getProgressBar [] (ItemStack. Material/FLINT_AND_STEEL))
                                                    (registerDefaultFuelTypes [] (gen-fuel (defaults/fuel-list :coal-generator-adv))))
                                   ]
                                  [{:capacity 64 :production 8}
                                   {:capacity 96 :production 10}
                                   {:capacity 128 :production 12}
                                   {:capacity 256 :production 18}
                                   ]))]
        (register (first each) (second each)))
      )))
