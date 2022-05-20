(ns testapp.db)

(def orders (atom [{:id "d2d5f824-7d1f-4720-a796-0a8ec81e85fd"
                    :title "Oil change"
                    :description "On average, vehicles are estimated to need an oil change every 3,000 miles or every six months."
                    :customer "Tim Cook"
                    :executor-id 1
                    :deadline #inst "2022-09-01T00:00:09.999-00:00"}
                   {:id "b5136245-0bae-43e1-a053-c0241720b2e4"
                    :title "Motor repair"
                    :description "Many motor repair shops will adjust the original winding design, including reducing wire size or the configuration, for convenience or ease of winding."
                    :customer "Jack Daniels"
                    :executor-id 2
                    :deadline #inst "2022-07-01T00:00:09.999-00:00"}]))