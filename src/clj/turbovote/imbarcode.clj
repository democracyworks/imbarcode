(ns turbovote.imbarcode
  "Top-level encoding functionality for generating IMbarcodes."
  (:require [turbovote.imbarcode.binary :refer [binary-encode]]
            [turbovote.imbarcode.codewords :refer [data->codewords]]
            [turbovote.imbarcode.crc :refer [IMb-fcs]]
            [turbovote.imbarcode.characters :refer [codewords->characters]]
            [turbovote.imbarcode.bars :refer [bars]]))

(defn ^:export encode
  "Given a barcode id, service type id, mailer id, serial number, and routing number,
   return a texual representation of the corresponding encoded USPS IMbarcode. The
   generated string will consist of the characters [ADFT].
   See section 3.1.3 of the IMb spec for a detailed description of the parameters."
  [barcode service mailer serial-number routing]
  (let [binary-data (binary-encode barcode service mailer serial-number routing)
        fcs (IMb-fcs binary-data)]
    (-> binary-data
        (data->codewords fcs)
        (codewords->characters fcs)
        bars)))
