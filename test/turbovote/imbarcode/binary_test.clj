(ns turbovote.imbarcode.binary-test
  (:require [clojure.test :refer :all]
            [turbovote.imbarcode.binary :refer :all]))

(deftest example-binary-encoding-test
  (let [test-cases
        {["01" "234" "567094" "987654321" ""] 0x1122103b5c2004b1
         ["01" "234" "567094" "987654321" "01234"] 0xd138a87bab5cf3804b1
         ["01" "234" "567094" "987654321" "012345678"] 0x202bdc097711204d21804b1
         ["01" "234" "567094" "987654321" "01234567891"] 0x16907b2a24abc16a2e5c004b1}]
    (doseq [[inputs output] test-cases]
      (is (= (apply binary-encode inputs) output)))))
