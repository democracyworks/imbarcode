(ns turbovote.imbarcode.crc-test
  (:require [clojure.test :refer :all]
            [turbovote.imbarcode.crc :refer :all]))

(deftest IMb-fcs-test
  (doseq [[data fcs] {0x00000000001122103B5C2004B1 0x051
                      0x0000000D138A87BAB5CF3804B1 0x065
                      0x000202BDC097711204D21804B1 0x606
                      0x016907B2A24ABC16A2E5C004B1 0x751}]
    (is (= (IMb-fcs data) fcs))))
