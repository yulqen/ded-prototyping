(ns ded-prototype.core-test
  (:require [clojure.test :refer :all]
            [ded-prototype.core :refer :all]))

(deftest a-test
  (testing "One should equal one."
    (is (= 1 1))))

(deftest basic
  (is (= (bobbins 10) 10))
  )
