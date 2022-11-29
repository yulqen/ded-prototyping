(ns ded-prototype.core
  (:gen-class)
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]))

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











