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

(defn lore-machine [^MachineTier tier ^MachineType type]
  (LoreBuilder/machine tier type))

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

(defn construct-tiered-machine [^String name-in-uppercase tier ^List lore]
  (let [key-name (-> name-in-uppercase .toLowerCase (.replaceAll "_" "-"))
        lore (ClojureUtils/consToList (cons "" lore))]
    {(keyword (str key-name "-" tier))
     (SlimefunItemStack. (str "DUMO_" name-in-uppercase "_" tier)
                         (with-tier generic tier) lore)}))

(defn fetch-items []
  (let [empty-lore (ClojureUtils/listToArr [])
        circuit-generic (CustomItemStack. Material/PAPER ["&f芯片"])
        coal-generator-generic (CustomItemStack. (head-texture :generator) "&c煤发电机" empty-lore)]
    (reduce merge [(binding [generic coal-generator-generic]
                     (construct-tiered-machine "COAL_GENERATOR" 1 [(lore-machine MachineTier/AVERAGE MachineType/GENERATOR)
                                                                   (lore-power-buf 64)
                                                                   (lore-power-per-sec 16)]))])))
