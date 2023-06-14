(ns io.sn.dumortierite.clj-module.setup
  (:import (io.sn.dumortierite.wrapper DumoWrapper)))

(defn setup [^DumoWrapper plug]
  (-> plug .getLogger (.warning "Hello from Clojure!!!")))
