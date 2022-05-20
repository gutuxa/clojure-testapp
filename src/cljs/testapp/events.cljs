(ns testapp.events
  (:require [re-frame.core :as rf]
            [ajax.edn :as ajax]
            [testapp.routes :as routes]
            [testapp.db :refer [initial-db]]))

(rf/reg-event-db ::initialize-db
  (constantly initial-db))

(rf/reg-event-fx ::init
  (fn [_ _]
    {:dispatch [::fetch-orders]}))

(rf/reg-fx :go-to
 (fn [route]
   (routes/go-to! route)))

(rf/reg-fx :log-error
 (fn [error]
   (js/console.error (pr-str error))))

(rf/reg-event-fx ::error-response
   (fn [_ [_ error]]
     {:log-error error}))

(rf/reg-event-fx ::fetch-orders
  (fn [_ _]
    {:http-xhrio {:method          :get
                  :uri             "/api/v1/order-list"
                  :format          (ajax/edn-request-format)
                  :response-format (ajax/edn-response-format)
                  :on-success      [::success-fetch-orders]
                  :on-failure      [::error-response]}}))

(rf/reg-event-db ::success-fetch-orders
  (fn [db [_ result]]
    (assoc db :orders result)))

(rf/reg-event-fx ::fetch-add-order
  (fn [_ [_ form callback]]
    {:http-xhrio {:method          :post
                   :uri             "/api/v1/new-order"
                   :params          form
                   :format          (ajax/edn-request-format)
                   :response-format (ajax/edn-response-format)
                   :on-success      [::success-add-order callback]
                   :on-failure      [::error-add-order callback]}}))

(rf/reg-event-fx ::success-add-order
  (fn [db [_ callback response]]
    (callback response)
    (when (:success? response)
      {:dispatch [::fetch-orders]
       :go-to :orders})))

(rf/reg-event-fx ::error-add-order
  (fn [db [_ callback response]]
    (if (get-in response [:response :errors])
      (callback (:response response))
      {:dispatch [::error-response response]})))

(rf/reg-event-db ::add-order
 (fn [db [_ order]]
   (assoc db :orders (conj (:orders db) order))))