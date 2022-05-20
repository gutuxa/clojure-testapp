(ns testapp.routes
  (:require [ring.middleware.resource :refer [wrap-resource]]
            [ring.util.response :refer [resource-response]]
            [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]
            [muuntaja.middleware :refer [wrap-format]]
            [testapp.api :as api]))

(defroutes approutes
  (GET "/api/v1/order-list" [] api/order-list)
  (POST "/api/v1/new-order" [] api/new-order)

  (GET "/*" [] (resource-response "index.html"))

  (route/not-found {:status 404
                    :body "Not found"}))

(def app
  (-> approutes
      (wrap-resource "static")
      (wrap-format)))