(ns testapp.views.order.list
  (:require [re-frame.core :as rf]
            [testapp.subs :as subs]
            [testapp.events :as events]
            [testapp.routes :as routes]))

(defn order-list-page []
  [:div
   [:h1 "Order List"]
   (let [orders @(rf/subscribe [::subs/orders])
         executors @(rf/subscribe [::subs/executors])]
      (if (empty? orders)
        [:div.orders__item.orders__empty "No orders"]
         (for [{:keys [id title description customer executor-id deadline]} orders]
          [:div.orders__item {:key id}
           [:div.orders__items-wrap
            [:h2 title]
            [:div.orders__label (.toLocaleDateString deadline)]
            ]
           [:div.orders__label "From: " customer]
           [:div.orders__label "To: " (get-in executors [executor-id :name])]
           [:div.orders__description description]])))
    [:a.button.button__primary.orders__button {:href (routes/url-for :new-order)} "Add"]])
