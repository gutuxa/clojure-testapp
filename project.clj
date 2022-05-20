(defproject testapp "0.1.0-SNAPSHOT"
  :description "Clojure Test Application"
  :license {:name "Eclipse Public License"
            :url "https://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [ring/ring-core "1.9.5"]
                 [ring/ring-jetty-adapter "1.9.5"]
                 [metosin/muuntaja "0.6.8"]
                 [compojure "1.6.2"]
                 [tick "0.5.0-RC5"]]
  :main ^:skip-aot testapp.core
  :source-paths ["src/clj" "src/cljc"]
  :uberjar-name "testapp.jar"
  :profiles {:uberjar {:aot :all}})
