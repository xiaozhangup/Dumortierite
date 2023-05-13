(ns io.sn.dumortierite.core
  (:gen-class
    :name io.sn.dumortierite.Entrance
    :extends io.sn.dumortierite.DumoPlugin)
  (:import (org.bukkit.plugin.java JavaPlugin)))

(set! *warn-on-reflection* true)

(defn -onEnable [^JavaPlugin this])
