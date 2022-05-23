(ns testapp.api
  (:require [clojure.instant :refer [read-instant-date]]
            [clojure.spec.alpha :as s]
            [testapp.spec :as spec]
            [testapp.db :as db]))

(defn order-list [_]
  (let [body (prn-str @db/orders)]
    {:status 200
     :headers {"Content-Type" "application/edn"}
     :body body}))

(defn new-order [request]
  (let [order (:body-params request)]
    (if (s/valid? :order/form order)
      (let [{:keys [title description customer executor-id deadline]} order]
        (swap! db/orders conj {:id (.toString (java.util.UUID/randomUUID))
                               :title title
                               :description description
                               :customer customer
                               :executor-id (Integer/parseInt executor-id)
                               :deadline (read-instant-date deadline)})
        {:status 200
         :headers {"Content-Type" "application/edn"}
         :body (prn-str {:success? true})})
      {:status 400
       :headers {"Content-Type" "application/edn"}
       :body (prn-str {:success? false
                       :errors (spec/get-error-messages (s/explain-data :order/form order))})})))

