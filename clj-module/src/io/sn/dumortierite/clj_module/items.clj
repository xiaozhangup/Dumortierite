(ns io.sn.dumortierite.clj-module.items
  (:import (io.github.thebusybiscuit.slimefun4.api.items SlimefunItemStack)
           (io.github.thebusybiscuit.slimefun4.core.attributes MachineTier MachineType)
           (io.github.thebusybiscuit.slimefun4.utils HeadTexture LoreBuilder)
           (io.sn.dumortierite.utils ConsUtils)
           (org.bukkit.inventory ItemStack)))

(set! *warn-on-reflection* true)

(defmacro defitem [^String id ^ItemStack item ^String name & lore]
  `(def ^{:static true
          :tag    SlimefunItemStack} ~(symbol id)
     (SlimefunItemStack. ~id ~item ~name (into-array String [~@lore]))))

(defitem "DUMO_COAL_GENERATOR_1" ConsUtils/HEAD_GENERATOR
         "&c煤发电机 &7- &fMk.&eI&r"
         ""
         (LoreBuilder/machine MachineTier/AVERAGE MachineType/GENERATOR)
         (LoreBuilder/powerBuffer 64)
         (LoreBuilder/powerPerSecond 16))

