(ns ded-prototype.core
  (:gen-class)
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]
            [clojure.spec.alpha :as s]))

;; quick look at clojure.spec
(s/valid? string? "tots") ;; => true

;; checking for two characteristics
(def n-gt-10 (s/and number? #(> % 10)))
(s/valid? n-gt-10 11) ;; => true

;; creating records
(defrecord Site [name location rp])
(defrecord Person [fname lname])

;; func to create site, based on the records
(defn make-site [name location ^Person rp]
  (->Site name location rp))

;; create a site
(def rrdl (make-site "Rosyth" "Rosyth Royal Dockyard" (->Person "Harold" "Clype")))

;; create another site
(def baes
  (->Site "BAES Barrow" "Barrow-in-Furness" (->Person "Todd" "Larken")))

;; get the RP for each site
(defn get-rps [& sites]
  (map :rp sites))

;; call that function
(get-rps rrdl baes)

;; data modelling using maps - superceded by defrecord above
(def organisations
  [
   {:name "Op 1" :type :operation}
   {:name "Op 2" :type :site}
   {:name "Op 3" :type :site}
   ])

;; basic filter on a map
(filter #(= (:type %) :operation) organisations)

;; import dscs from a csv file
(defn parse-csv [fn]
  (let [data (with-open [reader (io/reader fn)]
               (doall
                (csv/read-csv reader)))
        headers (first data)
        fields (rest data)]
    (for [f fields]
      (apply assoc {}
             (interleave [(keyword (last headers))
                          (keyword (first headers))]
                         [(first f) (read-string (last f))])))))

;; check that the func works
(parse-csv "dscs.csv")








