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

(def ^{:dynamic true
       :tag     ItemStack} generic)

(def ^{:dynamic true
       :tag     String} name-in-uppercase)

(defn construct-tiered-item [tier ^List lore]
  (let [key-name (-> name-in-uppercase .toLowerCase (.replaceAll "_" "-"))
        lore (ClojureUtils/consToList (cons "" lore))]
    {(keyword (str key-name "-" tier))
     (SlimefunItemStack. (str "DUMO_" name-in-uppercase "_" tier)
                         (with-tier generic tier) lore)}))

(defn fetch-items [id]
  (let [empty-lore (ClojureUtils/listToArr [])
        circuit-generic (CustomItemStack. Material/PAPER ["&f芯片"])
        coal-generator-generic (CustomItemStack. (head-texture :generator) "&c煤发电机" empty-lore)]
    (id (reduce merge [
                       ; Circuit
                       (binding [name-in-uppercase "CIRCUIT"
                                 generic circuit-generic]
                         (doseq [level (range 1 7)] (construct-tiered-item level []))
                         )

                       ; Coal Generator
                       (binding [name-in-uppercase "COAL_GENERATOR"
                                 generic coal-generator-generic]
                         (construct-tiered-item 1 [(lore-machine (machine-tier :average) (machine-type :generator))
                                                   (lore-power-buf 64)
                                                   (lore-power-per-sec 8)])
                         (construct-tiered-item 2 [(lore-machine (machine-tier :medium) (machine-type :generator))
                                                   (lore-power-buf 96)
                                                   (lore-power-per-sec 10)])
                         (construct-tiered-item 3 [(lore-machine (machine-tier :good) (machine-type :generator))
                                                   (lore-power-buf 128)
                                                   (lore-power-per-sec 12)])
                         (construct-tiered-item 4 ["&f使用先进的催化剂加快燃料燃烧速度",, ""
                                                   (lore-machine (machine-tier :advanced) (machine-type :generator))
                                                   (lore-power-buf 256)
                                                   (lore-power-per-sec 18)])
                         )
                       ]))))
