(ns imbarcode.codewords-test
  (:require [imbarcode.codewords :refer [data->codewords-base
                                         data->codewords]]
            [imbarcode.big-integer :as bi]
    #?@(:clj  [[clojure.test :refer [is deftest]]]
        :cljs [[cljs.test :refer-macros [is deftest]]])))

(deftest data->codewords-base-test
  (let [test-cases
        {(bi/from-string "00000000001122103B5C2004B1" 16)
         [0 0 0 0 559 202 508 451 124 17]
         (bi/from-string "0000000D138A87BAB5CF3804B1" 16)
         [0 0 15 14 290 567 385 48 388 333]
         (bi/from-string "000202BDC097711204D21804B1" 16)
         [0 110 1113 1363 198 413 470 468 1333 513]
         (bi/from-string "016907B2A24ABC16A2E5C004B1" 16)
         [14 787 607 1022 861 19 816 1294 35 301]}]
    (doseq [[input output] test-cases]
      (is (= (data->codewords-base input) output)))))

(deftest data->codewords-test
  (let [test-cases
        {[(bi/from-string "00000000001122103B5C2004B1" 16) 0x051]
         [0 0 0 0 559 202 508 451 124 34]
         [(bi/from-string "0000000D138A87BAB5CF3804B1" 16) 0x065]
         [0 0 15 14 290 567 385 48 388 666]
         [(bi/from-string "000202BDC097711204D21804B1" 16) 0x606]
         [659 110 1113 1363 198 413 470 468 1333 1026]
         [(bi/from-string "016907B2A24ABC16A2E5C004B1" 16) 0x751]
         [673 787 607 1022 861 19 816 1294 35 602]}]
    (doseq [[inputs output] test-cases]
      (is (= (apply data->codewords inputs) output)))))
