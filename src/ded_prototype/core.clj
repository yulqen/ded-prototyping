(ns ded-prototype.core
  (:gen-class)
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]))

(def fields (let [csv-data (with-open [reader (io/reader "dscs.csv")]
                             (doall
                              (csv/read-csv reader)))
                  csv-headers (first csv-data)
                  csv-fields (rest csv-data)]
              (for [f csv-fields]
                (hash-map :id (read-string (last f)) :name (first f)))))


fields












