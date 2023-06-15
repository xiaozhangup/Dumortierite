(ns io.sn.dumortierite.clj_module.defaults
  (:import (org.bukkit Material)
           (org.bukkit.inventory ItemStack)))


(defn im [^Material material]
  (ItemStack. material))

(defn fuel-list [id]
  (id {:coal-generator     [[8 (im Material/COAL)]
                            [80 (im Material/COAL_BLOCK)]
                            [12 (im Material/BLAZE_ROD)]
                            [20 (im Material/DRIED_KELP_BLOCK)]]
       :coal-generator-adv [[6 (im Material/COAL)]
                            [60 (im Material/COAL_BLOCK)]
                            [9 (im Material/BLAZE_ROD)]
                            [15 (im Material/DRIED_KELP_BLOCK)]]
       }))
