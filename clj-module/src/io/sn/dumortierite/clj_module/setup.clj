(ns io.sn.dumortierite.clj_module.setup
  (:import (io.sn.dumortierite DumoCore)))

(set! *warn-on-reflection* true)

(defn init [^DumoCore plug]
  (-> plug .getLogger (.warning "Hello from Clojure!!!"))
  )
