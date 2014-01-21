(ns turbovote.imbarcode-test
  #+clj (:require [clojure.test :refer :all]
                  [turbovote.imbarcode :refer :all])
  #+cljs (:require [cemerick.cljs.test :as t]
                   [turbovote.imbarcode :refer [encode]])
  #+cljs (:require-macros [cemerick.cljs.test
                           :refer (is deftest with-test run-tests testing test-var)]))

(deftest encode-test
  (let [test-cases
        {["01" "234" "567094" "987654321" ""]
         "ATTFATTDTTADTAATTDTDTATTDAFDDFADFDFTFFFFFTATFAAAATDFFTDAADFTFDTDT"
         ["01" "234" "567094" "987654321" "01234"]
         "DTTAFADDTTFTDTFTFDTDDADADAFADFATDDFTAAAFDTTADFAAATDFDTDFADDDTDFFT"
         ["01" "234" "567094" "987654321" "012345678"]
         "ADFTTAFDTTTTFATTADTAAATFTFTATDAAAFDDADATATDTDTTDFDTDATADADTDFFTFA"
         ["01" "234" "567094" "987654321" "01234567891"]
         "AADTFFDFTDADTAADAATFDTDDAAADDTDTTDAFADADDDTFFFDDTTTADFAAADFTDAADA"}]
    (doseq [[inputs output] test-cases]
      (is (= (apply encode inputs) output)))))
