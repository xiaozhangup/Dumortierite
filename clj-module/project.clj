(defproject clj-module "0.1.0"
  :description "designed to provide setup for main component"
  :url "https://github.com/freeze-dolphin/Dumortierite/clj-module"
  :license {:name "AGPL-3.0-only"
            :url  "https://www.gnu.org/licenses/agpl-3.0.txt"}
  :repositories [["papermc" "https://repo.papermc.io/repository/maven-public/"]
                 ["codemc" "https://repo.codemc.io/repository/maven-public/"]
                 ["jitpack" "https://jitpack.io/"]]
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [io.papermc.paper/paper-api "1.19.2-R0.1-SNAPSHOT"]
                 [com.github.HAPPYLAND-Dev/Slimefun4 "852b600f74"]
                 [org.jetbrains.kotlin/kotlin-stdlib "1.7.10"]
                 [de.tr7zw/item-nbt-api-plugin "2.11.3"]
                 ]
  :resource-paths ["lib/Dumortierite-0.1.0.jar"]
  :repl-options {:init-ns io.sn.dumortierite.clj-module.setup}
  :aot :all)
