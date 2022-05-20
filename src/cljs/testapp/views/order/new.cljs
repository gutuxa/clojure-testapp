(ns testapp.views.order.new
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [clojure.spec.alpha :as s]
            [testapp.spec :as spec]
            [testapp.subs :as subs]
            [testapp.events :as events]
            [testapp.routes :as routes]))

(def initial-form-state
  {:title ""
   :description ""
   :customer ""
   :executor-id ""
   :deadline ""})

(def form (r/atom initial-form-state))
(def form-summitted (r/atom false))
(def errors (r/atom {}))

(defn update-field [field event]
  (swap! form assoc field (-> event .-target .-value)))

(defn reset-state! []
  (reset! form initial-form-state)
  (reset! errors {})
  (reset! form-summitted false))

(defn callback [response]
  (if (:success? response)
    (reset-state!)
    (when (let [res-errors (:errors response)]
          (reset! errors res-errors)))))

(defn submit []
    (if (s/valid? :order/form @form)
      (-> (rf/dispatch [::events/fetch-add-order @form callback]))
      (reset! errors (spec/get-error-messages (s/explain-data :order/form @form)))))

(defn new-order-page []
  [:div
   [:h1 "New Order"]
   [:form
    [:label.label "Title"]
    [:input.input {:type "text"
                   :placeholder "John Doe"
                   :value (:title @form)
                   :class (when (:title @errors) "invalid")
                   :on-change #(update-field :title %)}]
    [:div.field-error (:title @errors)]
    [:label.label "Description"]
    [:textarea.textarea {:value (:description @form)
                         :class (when (:description @errors) "invalid")
                         :on-change #(update-field :description %)}]
    [:div.field-error (:description @errors)]
    [:label.label "Customer"]
    [:input.input {:type "text"
                   :placeholder "Tim Cook"
                   :value (:customer @form)
                   :class (when (:customer @errors) "invalid")
                   :on-change #(update-field :customer %)}]
    [:div.field-error (:customer @errors)]
    [:label.label "Executor"]
    [:select.select {:required true
                     :value (:executor-id @form)
                     :class (when (:executor-id @errors) "invalid")
                     :on-change #(update-field :executor-id %)}
     [:option {:value ""
               :disabled true
               :hidden true} "Please choose..."]
     (let [executors @(rf/subscribe [::subs/executors])]
      (for [{:keys [id name]} (vals executors)]
        [:option {:key id
                  :value id} name]))]
    [:div.field-error (:executor-id @errors)]
    [:label.label "Deadline"]
    [:input.input {:type "text"
                   :placeholder "2022-12-24"
                   :value (:deadline @form)
                   :class (when (:deadline @errors) "invalid")
                   :on-change #(update-field :deadline %)}]]
   [:div.field-error (:deadline @errors)]
   [:div.new-order__buttons
    [:a.button.button__secondary.new-order__button {:href (routes/url-for :orders)} "To List"]
    [:button.button.button__primary.new-order__button {:on-click submit} "Add"]]])