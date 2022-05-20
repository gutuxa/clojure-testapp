(ns testapp.core
  (:gen-class)
  (:require [ring.adapter.jetty :as jetty]
            [testapp.routes :refer [app]]))

(defn -main [& args]
  (jetty/run-jetty app {:port 3000 :join? false}))