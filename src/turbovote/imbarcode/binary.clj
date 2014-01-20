(ns turbovote.imbarcode.binary
  (:require [clojure.string :as s :only [replace]]))

(defn only-digits? [string]
  (re-matches #"\A\d*\z" string))

(defn str->ints [string]
  {:pre [(only-digits? string)]}
  (map #(-> % str read-string) string))

(defn read-decimal-number [string]
  (-> string
      (s/replace #"^0+" "")
      read-string))

(defn convert-routing-code [code]
  {:pre [(some #{(count code)} [0 5 9 11])
         (only-digits? code)]}
  (case (count code)
    0 0
    5 (inc (read-decimal-number code))
    9 (+ (read-decimal-number code) 100000 1)
    11 (+ (read-decimal-number code) 1000000000 100000 1)))

(defn binary-attach-digit [binary digit]
  (-> binary
      (*' 10)
      (+' digit)))

(defn binary-encode [barcode service mailer serial-number routing]
  (let [binary (convert-routing-code routing)
        tracking-code-digits (str->ints (str barcode service mailer serial-number))
        with-barcode-type (-> binary
                              (*' 10)
                              (+' (first tracking-code-digits))
                              (*' 5)
                              (+' (second tracking-code-digits)))]
    (reduce binary-attach-digit with-barcode-type (drop 2 tracking-code-digits))))
