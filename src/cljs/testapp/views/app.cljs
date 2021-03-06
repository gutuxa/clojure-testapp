(ns testapp.views.app
  (:require [re-frame.core :as rf]
            [testapp.subs :as subs]
            [testapp.views.order.list :refer [order-list-page]]
            [testapp.views.order.new :refer [new-order-page]]))

(defn app []
  (let [route @(rf/subscribe [::subs/route])]
    (case (:handler route)
     :orders (order-list-page)
     :new-order (new-order-page)
     :not-found "Page not found")))
