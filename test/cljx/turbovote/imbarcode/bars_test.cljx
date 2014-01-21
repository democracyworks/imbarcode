(ns turbovote.imbarcode.bars-test
  #+clj (:require [clojure.test :refer :all]
                  [turbovote.imbarcode.bars :refer :all])
  #+cljs (:require [cemerick.cljs.test :as t]
                   [turbovote.imbarcode.bars :refer [bars]])
  #+cljs (:require-macros [cemerick.cljs.test
                           :refer (is deftest with-test run-tests testing test-var)]))

(deftest bars-test
  (let [test-cases
        {[0x1FE0 0x001F 0x001F 0x001F 0x0ADB 0x01A3 0x1BC3 0x1838 0x012B 0x0076]
         "ATTFATTDTTADTAATTDTDTATTDAFDDFADFDFTFFFFFTATFAAAATDFFTDAADFTFDTDT"
         [0x1FE0 0x001F 0x02BF 0x0057 0x0255 0x18DB 0x1B17 0x009D 0x030B 0x0583]
         "DTTAFADDTTFTDTFTFDTDDADADAFADFATDDFTAAAFDTTADFAAATDFDTDFADDDTDFFT"
         [0x1154 0x1F07 0x01FE 0x0110 0x019A 0x1298 0x03A2 0x03A1 0x0084 0x14EE]
         "ADFTTAFDTTTTFATTADTAAATFTFTATDAAAFDDADATATDTDTTDFDTDATADADTDFFTFA"
         [0x0DCB 0x085C 0x08E4 0x0B06 0x06DD 0x1740 0x17C6 0x1200 0x123F 0x1B2B]
         "AADTFFDFTDADTAADAATFDTDDAAADDTDTTDAFADADDDTFFFDDTTTADFAAADFTDAADA"}]
    (doseq [[input output] test-cases]
      (is (= (bars input) output)))))
