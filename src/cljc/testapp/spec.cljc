(ns testapp.spec
  (:require [clojure.spec.alpha :as s]
            [tick.core :as t]))

(def spec-errors
  {::non-empty-string "Required field"
   ::int-above-zero "Required field"
   ::valid-date "Invalid date (date format 2022-12-22)"
   ::date-in-future "Date must be in the future"})

(defn get-error [problem]
  (let [{:keys [via in]} problem
        spec (last via)
        field (last in)]
    {field (get spec-errors spec)}))

(defn get-error-messages [data]
  (->> (::s/problems data)
        (reduce #(conj %1 (get-error %2)) {})))

(s/def ::non-empty-string
   (s/and string?
          not-empty))

(defn ->int [str]
  (#?(:clj  Integer/parseInt
      :cljs int) str))

(s/def ::->int
   #(->int %))

(s/def ::int-above-zero
   (s/and ::non-empty-string
          ::->int
          #(> (->int %) 0)))

(s/def ::valid-date
   (s/conformer
    (fn [value]
      (try (t/date value)
      (catch #?(:clj  Exception
                :cljs :default) _
        ::s/invalid)))))

(s/def ::date-in-future
   (s/and ::non-empty-string
          ::valid-date
          (fn [value]
            (t/< (t/today) (t/date value)))))

(s/def :order/title ::non-empty-string)
(s/def :order/description ::non-empty-string)
(s/def :order/customer ::non-empty-string)
(s/def :order/executor-id ::int-above-zero)
(s/def :order/deadline ::date-in-future)

(s/def :order/form (s/keys :req-un [:order/title :order/description :order/customer :order/executor-id :order/deadline]))