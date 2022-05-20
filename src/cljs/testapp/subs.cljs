(ns testapp.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub ::route
 (fn [db]
   (:route db {:handler :not-found})))

(rf/reg-sub ::orders
  (fn [db]
    (:orders db [])))

(rf/reg-sub ::executors
  (fn [db]
    (:executors db [])))