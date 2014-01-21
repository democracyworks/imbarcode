(ns turbovote.imbarcode.crc-test
  #+clj (:require [clojure.test :refer :all]
                  [turbovote.imbarcode.crc :refer :all]
                  [turbovote.big-integer :as bi])
  #+cljs (:require [cemerick.cljs.test :as t]
                   [turbovote.imbarcode.crc :refer [IMb-fcs]]
                   [turbovote.big-integer :as bi])
  #+cljs (:require-macros [cemerick.cljs.test
                           :refer (is deftest with-test run-tests testing test-var)]))

(deftest IMb-fcs-test
  (doseq [[data fcs] {(bi/from-string "00000000001122103B5C2004B1" 16) 0x051
                      (bi/from-string "0000000D138A87BAB5CF3804B1" 16) 0x065
                      (bi/from-string "000202BDC097711204D21804B1" 16) 0x606
                      (bi/from-string "016907B2A24ABC16A2E5C004B1" 16) 0x751}]
    (is (= (IMb-fcs data) fcs))))
