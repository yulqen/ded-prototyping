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
(defrecord Person [fname lname email mobile])

;; func to create site, based on the records
(defn make-site [name location ^Person rp]
  (->Site name location rp))

;; create a site
(def rrdl (make-site "Rosyth" "Rosyth Royal Dockyard"
                     (->Person
                      "Harold"
                      "Clype"
                      "toss@toss.com"
                      "07004 465462")))

;; create another site
(def baes
  (->Site "BAES Barrow" "Barrow-in-Furness"
          (->Person
           "Todd"
           "Larken"
           "toss@tonks.com"
           "07866 046516")))

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

;; parse dsc csv to records
(defrecord DSC [id name])

;; func to convert the csv into a vector of DSC records
(defn parse-csv-to-records [fn]
  (let [data (with-open [reader (io/reader fn)]
               (doall
                (csv/read-csv reader)))
        headers (first data)
        fields (rest data)]
    (vec (for [f fields]
       (->DSC (read-string (last f)) (first f))))))


;; check that the func works
(parse-csv-to-records "dscs.csv")








