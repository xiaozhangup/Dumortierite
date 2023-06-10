(ns io.sn.dumortierite.clj.base
  (:gen-class
    :extends CraftingOperationMachineBase
    :prefix "-")
  (:import (io.github.thebusybiscuit.slimefun4.libraries.dough.items CustomItemStack)
           (io.github.thebusybiscuit.slimefun4.utils ChestMenuUtils)
           (io.sn.dumortierite.wrapper CraftingOperationMachineBase)
           (me.mrCookieSlime.CSCoreLibPlugin.general.Inventory ChestMenu$AdvancedMenuClickHandler ChestMenu$MenuClickHandler)
           (me.mrCookieSlime.Slimefun.api.inventory BlockMenuPreset)
           (org.bukkit Material)
           (org.bukkit.inventory ItemStack)))

(set! *warn-on-reflection* true)

(def BORDER [])
(def BORDER_IN [])
(def BORDER_OUT [])

(def SLOT_OUTPUT [])

(def INDICATOR 22)

(defn -constructMenu [^BlockMenuPreset preset]
  (doseq [i BORDER]
    (.addItem preset i (ChestMenuUtils/getBackground) (ChestMenuUtils/getEmptyClickHandler)))

  (doseq [i BORDER_IN]
    (.addItem preset i (ChestMenuUtils/getInputSlotTexture) (ChestMenuUtils/getEmptyClickHandler)))

  (doseq [i BORDER_OUT]
    (.addItem preset i (ChestMenuUtils/getOutputSlotTexture) (ChestMenuUtils/getEmptyClickHandler)))

  (.addItem preset INDICATOR (CustomItemStack. Material/BLACK_STAINED_GLASS_PANE [" "]) (ChestMenuUtils/getEmptyClickHandler))

  (doseq [i SLOT_OUTPUT]
    (.addMenuClickHandler preset i (reify ChestMenu$AdvancedMenuClickHandler
                                     (onClick [_ p slot cursor action] false)
                                     (onClick [_ e p slot cursor action] (or (nil? cursor) (= (.getType cursor) Material/AIR)))))))
