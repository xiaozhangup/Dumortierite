(ns io.sn.dumortierite.clj_module.items
  (:import (io.github.thebusybiscuit.slimefun4.api.items SlimefunItemStack)
           (io.github.thebusybiscuit.slimefun4.core.attributes MachineTier MachineType)
           (io.github.thebusybiscuit.slimefun4.libraries.dough.items CustomItemStack)
           (io.github.thebusybiscuit.slimefun4.utils LoreBuilder SlimefunUtils)
           (io.sn.dumortierite.utils ClojureUtils ItemEffectUtils)
           (java.util List)
           (org.bukkit Material)
           (org.bukkit.inventory ItemStack)))

(set! *warn-on-reflection* true)

(defn machine-type [k]
  (k {:machine   MachineType/MACHINE
      :generator MachineType/GENERATOR}))

(defn machine-tier [k]
  (k {:basic    MachineTier/BASIC
      :average  MachineTier/AVERAGE
      :medium   MachineTier/MEDIUM
      :good     MachineTier/GOOD
      :advanced MachineTier/ADVANCED
      :end-game MachineTier/END_GAME}))

(defn lore-machine [tier type]
  (LoreBuilder/machine (machine-tier tier) (machine-type type)))

(defn lore-power-buf [buf]
  (LoreBuilder/powerBuffer buf))

(defn lore-power-per-sec [pow]
  (LoreBuilder/powerPerSecond pow))

(defmacro field-by-name [^String class ^String field]
  `(eval (symbol (str ~class "/" ~field))))

(defn ^ItemStack get-custom-head [^String base64]
  (SlimefunUtils/getCustomHead base64))

(defn ^ItemStack head-texture [name]
  (name {:generator (get-custom-head "9343ce58da54c79924a2c9331cfc417fe8ccbbea9be45a7ac85860a6c730")}))

(defn ^ItemStack with-tier [^ItemStack item tier]
  (.withTier ItemEffectUtils/Companion item tier))

(defn construct-tiered-item [^String name-in-uppercase ^ItemStack generic tier ^List lore]
  (let [key-name (-> name-in-uppercase .toLowerCase (.replaceAll "_" "-"))
        lore (ClojureUtils/consToList (cons "" lore))]
    {(keyword (str key-name "-" tier))
     (SlimefunItemStack. (str "DUMO_" name-in-uppercase "_" tier)
                         (with-tier generic tier) lore)}))

(defn fetch-items [id]
  (let [empty-lore (ClojureUtils/listToArr [])
        circuit-generic (CustomItemStack. Material/PAPER ["&f芯片"])
        coal-generator-generic (CustomItemStack. (head-texture :generator) "&c煤发电机" empty-lore)
        result
        (reduce
          merge
          (reduce
            concat
            [(let [^String name "CIRCUIT"
                   ^ItemStack generic circuit-generic]
               (for [level (range 1 7)] (construct-tiered-item name generic level [])))

             (let [^String name "COAL_GENERATOR"
                   ^ItemStack generic coal-generator-generic]
               [(construct-tiered-item name generic 1 [(lore-machine :average :generator) (lore-power-buf 64) (lore-power-per-sec 8)])
                (construct-tiered-item name generic 2 [(lore-machine :medium :generator) (lore-power-buf 96) (lore-power-per-sec 10)])
                (construct-tiered-item name generic 3 [(lore-machine :good :generator) (lore-power-buf 128) (lore-power-per-sec 12)])
                (construct-tiered-item name generic 4 ["&f使用先进的催化剂加快燃料燃烧速度" "" (lore-machine :advanced :generator) (lore-power-buf 256) (lore-power-per-sec 18)])])]))]
    (id result)))
