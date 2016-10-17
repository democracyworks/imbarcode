(ns imbarcode.core-test
  (:require [imbarcode.core :refer [encode
                                    encodable?
                                    barcode-id:default
                                    service-type-id:origin
                                    split-structure-digits
                                    origin-service-types]]
            [clojure.test.check]
            [clojure.test.check.generators :as gen]
    #?@(:clj  [[clojure.data.csv :as csv]
               [clojure.java.io :as io]
               [clojure.test :refer [is deftest testing]]
               [clojure.test.check.properties :as prop]
               [clojure.test.check.clojure-test :refer [defspec]]]
        :cljs [[cljs.test :refer-macros [is deftest testing]]
               [clojure.test.check.properties :as prop :include-macros true]
               [clojure.test.check.clojure-test :refer-macros [defspec]]])))

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
        (is (= (apply encode inputs) output))
        (is (= (encode (apply str inputs)) output)))))
  (testing "origin"
    (let [test-cases
          {["00" "050" "010203123456789" "012345678"]
           "FTTATTTTTDFAADTATAFTFTTTAAAAFTTDDDTFTDDAFTADDFDTDAFTDATTDFADDDDDA"
           [barcode-id:default service-type-id:origin "999888777666555" "012345678"]
           "FAADADDDTAFTAFDFFTDAADADTAFTTDATAAFTAFTTFAATAAFTFATDTFATFFTATTAAF"}]
      (doseq [[inputs output] test-cases]
        (is (= (apply encode inputs) output))
        (is (= (encode (apply str inputs)) output)))))
  #?(:clj
      (testing "USPS IMb encoder reference test"
        (with-open [rdr (io/reader (io/resource "imb-reference/usps-imb-encoder-test-cases.csv"))]
          (doseq [[n tracking routing bar ret msg] (csv/read-csv rdr)]
            (testing (str "case " n)
              ; Break up reference tracking number, if it exists
              (let [[_ bid stid cust] (re-matches #"(.{0,2})(.{0,3})(.*)" (str tracking))]
                ; "00" in the reference data should succeed, anything else fails
                (if (= "00" ret)
                  (is (= bar (encode bid stid cust routing)) msg)
                  (is (try
                        (nil? (encode bid stid cust routing))
                        (catch AssertionError _ true))
                      msg)))))))))

(def routing-code
  (gen/fmap #(apply str %)
            (gen/one-of
             [(gen/return [])
              (gen/vector (gen/choose 0 9) 5)
              (gen/vector (gen/choose 0 9) 9)
              (gen/vector (gen/choose 0 9) 11)])))

(def barcode
  (gen/fmap #(apply str %)
            (gen/tuple (gen/choose 0 9)
                       (gen/choose 0 4))))

(def gen-structure-digits
  (gen/fmap #(apply str %)
   (gen/tuple barcode
              (gen/fmap #(apply str %)
                        (gen/vector (gen/choose 0 9) 18))
              routing-code)))

(defspec encode-spec 1000
  (prop/for-all [structure-digits gen-structure-digits]
    (let [encoded (encode structure-digits)]
      (and encoded
           (every? #{\A \D \F \T} encoded)))))

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
