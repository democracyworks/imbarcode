(ns turbovote.imbarcode
  (:require [turbovote.imbarcode.binary :refer [binary-encode]]
            [turbovote.imbarcode.codewords :refer [data->codewords]]
            [turbovote.imbarcode.crc :refer [IMb-fcs]]
            [turbovote.imbarcode.characters :refer [codewords->characters]]
            [turbovote.imbarcode.bars :refer [bars]]))

(defn encode [barcode service mailer serial-number routing]
  (let [binary-data (binary-encode barcode service mailer serial-number routing)
        fcs (IMb-fcs binary-data)]
    (-> binary-data
        (data->codewords fcs)
        (codewords->characters fcs)
        bars)))
