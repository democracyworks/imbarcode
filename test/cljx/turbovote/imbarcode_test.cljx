(ns turbovote.imbarcode-test
  #+clj (:require [clojure.test :refer :all]
                  [turbovote.imbarcode :refer :all])
  #+cljs (:require [cemerick.cljs.test :as t]
                   [turbovote.imbarcode :refer [encode barcode-id:default
                                                service-type-id:origin
                                                split-structure-digits
                                                origin-service-types]])
  #+cljs (:require-macros [cemerick.cljs.test
                           :refer (is deftest with-test run-tests testing test-var)]))

(deftest encode-test
  (testing "destination"
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
  (testing "origin"
    (let [test-cases
          {["00" "050" "010203123456789" "012345678"]
           "FTTATTTTTDFAADTATAFTFTTTAAAAFTTDDDTFTDDAFTADDFDTDAFTDATTDFADDDDDA"
           [barcode-id:default service-type-id:origin "999888777666555" "012345678"]
           "FAADADDDTAFTAFDFFTDAADADTAFTTDATAAFTAFTTFAATAAFTFATDTFATFFTATTAAF"}]
      (doseq [[inputs output] test-cases]
        (is (= (apply encode inputs) output))))))

(deftest split-structure-digits-test
  (testing "destination"
    (let [barcode "01"
          service "234"
          mailer "123456"
          serial "000111222"
          routing "80210"
          structure-digits (str barcode service mailer serial routing)
          result (split-structure-digits structure-digits)]
      (is (= barcode (:barcode result)))
      (is (= service (:service result)))
      (is (= mailer (get-in result [:6-digit-mailer :mailer-id])))
      (is (= serial (get-in result [:6-digit-mailer :serial-number])))
      (is (= routing (:routing result)))))
  (testing "origin"
    (let [barcode "02"
          service (first origin-service-types)
          customer-number "123456789012345"
          routing "80210"
          structure-digits (str barcode service customer-number routing)
          result (split-structure-digits structure-digits)]
      (is (= barcode (:barcode result)))
      (is (= service (:service result)))
      (is (= customer-number (:customer-number result)))
      (is (= routing (:routing result))))))
