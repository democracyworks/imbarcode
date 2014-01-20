(ns turbovote.imbarcode.binary
  (:require [turbovote.big-integer :as bi]))

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

(defn binary-encode [barcode service mailer serial-number routing]
  (let [binary (convert-routing-code routing)
        tracking-code-digits (str->ints (str barcode service mailer serial-number))
        with-barcode-type (-> binary
                              (bi/* 10)
                              (bi/+ (first tracking-code-digits))
                              (bi/* 5)
                              (bi/+ (second tracking-code-digits)))]
    (reduce binary-attach-digit with-barcode-type (drop 2 tracking-code-digits))))
