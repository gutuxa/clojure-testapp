(ns testapp.core
  (:require [reagent.dom :as rd]
            [re-frame.core :as rf]
            [day8.re-frame.http-fx]
            [testapp.routes :as routes]
            [testapp.events :as events]
            [testapp.views.app :refer [app]]))

(defn render []
  (rd/render [app]
             (.getElementById js/document "app")))

(defn init []
  (rf/dispatch-sync [::events/initialize-db])
  (rf/dispatch [::events/init])
  (routes/start!)
  (render))