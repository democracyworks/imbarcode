(ns imbarcode.binary-test
  (:require [imbarcode.binary :refer [binary-encode]]
            [imbarcode.big-integer :as bi]
    #?@(:clj  [[clojure.test :refer [is deftest]]]
        :cljs [[cljs.test :refer-macros [is deftest]]])))

(deftest example-binary-encoding-test
  (let [test-cases
        {["01" "234" "567094" "987654321" ""]
         (bi/from-string "1122103b5c2004b1" 16)
         ["01" "234" "567094" "987654321" "01234"]
         (bi/from-string "d138a87bab5cf3804b1" 16)
         ["01" "234" "567094" "987654321" "012345678"]
         (bi/from-string "202bdc097711204d21804b1" 16)
         ["01" "234" "567094" "987654321" "01234567891"]
         (bi/from-string "16907b2a24abc16a2e5c004b1" 16)}]
    (doseq [[inputs output] test-cases]
      (let [actual (apply binary-encode inputs)]
        (is #?(:clj (= actual output)
               :cljs (.equals actual output)))))))
