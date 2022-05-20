(ns testapp.routes
  (:require [re-frame.core :as rf]
            [bidi.bidi :as bidi]
            [pushy.core :as pushy]))

(def routes
  ["/" {""    :orders
        "new" :new-order}])

(rf/reg-event-db ::set-route
  (fn [db [_ new-route]]
    (assoc db :route new-route)))

(def url-for
  (partial bidi/path-for routes))

(defn- parse [url]
  (bidi/match-route routes url))

(defn- dispatch [match]
  (rf/dispatch [::set-route match]))

(defonce history
  (pushy/pushy dispatch parse))

(defn go-to! [route]
  (pushy/set-token! history (url-for route)))

(defn start! []
  (pushy/start! history))
