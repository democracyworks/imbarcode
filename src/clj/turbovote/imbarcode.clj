(ns turbovote.imbarcode
  "Top-level encoding functionality for generating IMbarcodes."
  (:require [turbovote.imbarcode.binary :refer [binary-encode]]
            [turbovote.imbarcode.codewords :refer [data->codewords]]
            [turbovote.imbarcode.crc :refer [IMb-fcs]]
            [turbovote.imbarcode.characters :refer [codewords->characters]]
            [turbovote.imbarcode.bars :refer [bars]]))

(defn- encode-binary-data [binary-data]
  (let [fcs (IMb-fcs binary-data)]
    (-> binary-data
        (data->codewords fcs)
        (codewords->characters fcs)
        bars)))

(def ^:export barcode-id:default "00")

(def ^:export service-type-id:destination "040")
(def ^:export service-type-id:origin "050")

(defn ^:export encode
  "Generate the texual representation of an encoded USPS IMbarcode
   from the data provided. For origin tracing, pass in a barcode id,
   service type id, customer number, and routing number.  For
   destination tracing, pass in a barcode id, service type id, mailer
   id, serial number, and routing number.

   The generated string will consist of the characters [ADFT].  See
   section 3.1.3 of the IMb spec for a detailed description of the
   parameters."
  ([barcode service customer-number routing]
     {:pre [(every? string? [barcode service customer-number routing])
            (= (count barcode) 2)
            (= (count service) 3)
            (= (count customer-number) 15)
            (#{0 5 9 11} (count routing))]}
     (encode-binary-data
      (binary-encode barcode service customer-number routing)))
  ([barcode service mailer serial-number routing]
     {:pre [(every? string? [barcode service mailer serial-number routing])
            (= (count barcode) 2)
            (= (count service) 3)
            (or (and (= (count mailer) 6)
                     (= (count serial-number) 9))
                (and (= (count mailer) 9)
                     (= (count serial-number) 6)))
            (#{0 5 9 11} (count routing))]}
     (encode-binary-data
      (binary-encode barcode service mailer serial-number routing))))
