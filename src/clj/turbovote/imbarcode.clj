(ns turbovote.imbarcode
  "Top-level encoding functionality for generating IMbarcodes."
  (:require [turbovote.imbarcode.binary :refer [binary-encode]]
            [turbovote.imbarcode.codewords :refer [data->codewords]]
            [turbovote.imbarcode.crc :refer [IMb-fcs]]
            [turbovote.imbarcode.characters :refer [codewords->characters]]
            [turbovote.imbarcode.bars :refer [bars]]))

(defn encode-binary-data [binary-data]
  (let [fcs (IMb-fcs binary-data)]
    (-> binary-data
        (data->codewords fcs)
        (codewords->characters fcs)
        bars)))

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
     (encode-binary-data
      (binary-encode barcode service customer-number routing)))
  ([barcode service mailer serial-number routing]
     (encode-binary-data
      (binary-encode barcode service mailer serial-number routing))))
