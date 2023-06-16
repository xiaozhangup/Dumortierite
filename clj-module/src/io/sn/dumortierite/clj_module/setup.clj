(ns io.sn.dumortierite.clj_module.setup
  (:require
    [io.sn.dumortierite.clj_module.defaults :as defaults]
    [io.sn.dumortierite.clj_module.items :as items])
  (:import
    (io.github.thebusybiscuit.slimefun4.api.items ItemGroup SlimefunItem)
    (io.github.thebusybiscuit.slimefun4.api.recipes RecipeType)
    (io.github.thebusybiscuit.slimefun4.libraries.dough.items CustomItemStack)
    (io.github.thebusybiscuit.slimefun4.utils SlimefunUtils)
    (io.sn.dumortierite DumoCore)
    (io.sn.dumortierite.abstracts CoalGenerator)
    (org.bukkit Material NamespacedKey)
    (org.bukkit.event EventHandler Listener)
    (org.bukkit.event.player PlayerJoinEvent)
    (org.bukkit.inventory ItemStack)))

(set! *warn-on-reflection* true)

(defn im [^Material material]
  (ItemStack. material))

(declare ^{:dynamic true :tag DumoCore} plug)

(defmacro gen-fuel [fuel-list]
  `(for [fuel# ~fuel-list]
     (.registerFuel (first fuel#) (second fuel#))))

(defmacro register
  ([^SlimefunItem sf-item {capacity :capacity production :production}]
   `(-> ~sf-item (.setCapacity 128) (.setEnergyProduction 16) (.register plug)))
  ([^SlimefunItem sf-item]
   `(-> ~sf-item (.register plug))))

(defn setup [^DumoCore plug]
  (-> plug .getLogger (.warning "Setup section now begin..."))

  (-> plug .getServer .getPluginManager (.registerEvents (proxy [Listener] []
                                                           (^:EventHandler onJoin [^PlayerJoinEvent evt]
                                                             (-> evt .getPlayer .getInventory (.addItem (items/fetch-items "circuit-5"))))) plug))

  (let [defa-recipe (into-array ItemStack (repeat 9 nil))
        defa-type RecipeType/NULL
        group (ItemGroup. (NamespacedKey. plug "dumortierite") (CustomItemStack.
                                                                 (SlimefunUtils/getCustomHead "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWZhYzhhMGMxM2E1YTM2NzQ3NjBhOGY1ZTNkMWEyMzQwYWRlYmJjMWE1ODY1M2JlZTk1NjgzMjRhMWViMzNjYSJ9fX0=")
                                                                 ["蓝线石 : Dumortierite Craft&f"]) 4)]
    (binding [plug plug]
      ; Circuit
      (doseq [level (range 1 7)]
        (register (SlimefunItem. group (items/fetch-items (keyword (str "circuit-" level))) defa-type defa-recipe)))

      ; Coal Generator
      (doseq [each
              [[(proxy [CoalGenerator] [group (items/fetch-items :coal-generator-1) defa-recipe defa-type]
                  (getProgressBar [] (ItemStack. Material/FLINT_AND_STEEL))
                  (registerDefaultFuelTypes [] (gen-fuel (defaults/fuel-list :coal-generator)))) {:capacity 64 :production 8}]
               [(proxy [CoalGenerator] [group (items/fetch-items :coal-generator-2) defa-recipe defa-type]
                  (getProgressBar [] (ItemStack. Material/FLINT_AND_STEEL))
                  (registerDefaultFuelTypes [] (gen-fuel (defaults/fuel-list :coal-generator)))) {:capacity 96 :production 10}]
               [(proxy [CoalGenerator] [group (items/fetch-items :coal-generator-3) defa-recipe defa-type]
                  (getProgressBar [] (ItemStack. Material/FLINT_AND_STEEL))
                  (registerDefaultFuelTypes [] (gen-fuel (defaults/fuel-list :coal-generator)))) {:capacity 128 :production 12}]
               [(proxy [CoalGenerator] [group (items/fetch-items :coal-generator-4) defa-recipe defa-type]
                  (getProgressBar [] (ItemStack. Material/FLINT_AND_STEEL))
                  (registerDefaultFuelTypes [] (gen-fuel (defaults/fuel-list :coal-generator-adv)))) {:capacity 256 :production 18}]]]
        (register (first each) (second each))))))
