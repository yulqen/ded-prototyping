(ns ded-prototype.core
  (:gen-class)
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]
            [clojure.spec.alpha :as s]))

(s/valid? string? "tots") ;; => true

(def n-gt-10 (s/and number? #(> % 10)))
(s/valid? n-gt-10 11) ;; => true

(defrecord Site [name location rp])
(defrecord Person [fname lname])

(def baes
  (->Site "BAES Barrow" "Barrow-in-Furness" (->Person "Todd" "Larken")))

(def coll-string? (s/coll-of string?))

;; data modelling using maps
(def organisations
  [
   {:name "Op 1" :type :operation}
   {:name "Op 2" :type :site}
   {:name "Op 3" :type :site}
   ])

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

(parse-csv "dscs.csv")

(filter #(= (:type %) :operation) organisations)










