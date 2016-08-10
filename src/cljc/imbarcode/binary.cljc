(ns imbarcode.binary
  "Handles conversion of data fields into binary data.
   See section 3.2.1 of the IMb spec."
  (:require [imbarcode.big-integer :as bi]))

(defn str->ints [string]
  {:pre [(bi/valid-string? string)]}
  (map #(-> % str bi/from-string) string))

(defn convert-routing-code [code]
  {:pre [(some #{(count code)} [0 5 9 11])
         (bi/valid-string? code)]}
  (case (count code)
    0 0
    5 (bi/inc (bi/from-string code))
    9 (bi/+ (bi/from-string code) 100000 1)
    11 (bi/+ (bi/from-string code) 1000000000 100000 1)))

(defn binary-attach-digit [binary digit]
  (-> binary
      (bi/* 10)
      (bi/+ digit)))

(defn binary-encode-collapsed-fields
  [front-fields routing]
  (let [binary (convert-routing-code routing)
        tracking-code-digits (str->ints front-fields)
        with-barcode-type (-> binary
                              (bi/* 10)
                              (bi/+ (first tracking-code-digits))
                              (bi/* 5)
                              (bi/+ (second tracking-code-digits)))]
    (reduce binary-attach-digit with-barcode-type (drop 2 tracking-code-digits))))

(defn binary-encode
  ([barcode service customer-number routing]
   (binary-encode-collapsed-fields (str barcode service customer-number)
                                   routing))
  ([barcode service mailer serial-number routing]
   (binary-encode-collapsed-fields (str barcode service mailer serial-number)
                                   routing)))
