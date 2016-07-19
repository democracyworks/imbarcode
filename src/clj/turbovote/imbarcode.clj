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

(def origin-service-types #{"050" "051" "052"})

(defn ^:export split-structure-digits
  "Split IMbarcode structure digits into their constituent parts"
  [imb]
  (let [barcode (subs imb 0 2)
        service (subs imb 2 5)
        routing (subs imb 20)
        response {:barcode barcode
                  :service service
                  :routing routing}]
    (if (origin-service-types service)
      (assoc response :customer-number (subs imb 5 20))
      (let [mailer-and-serial (subs imb 5 20)]
        (assoc response
          :6-digit-mailer {:mailer-id (subs mailer-and-serial 0 6)
                           :serial-number (subs mailer-and-serial 6)}
          :9-digit-mailer {:mailer-id (subs mailer-and-serial 0 9)
                           :serial-number (subs mailer-and-serial 9)})))))

(defn ^:export encode
  "Generate the texual representation of an encoded USPS IMbarcode
   from the data provided. You can pass in the full numerical
   sequence if you have that available. For origin tracing, pass in
   a barcode id, service type id, customer number, and routing
   number.  For destination tracing, pass in a barcode id, service
   type id, mailer id, serial number, and routing number.

   The generated string will consist of the characters [ADFT].  See
   section 3.1.3 of the IMb spec for a detailed description of the
   parameters."
  ([structure-digits]
     {:pre [(string? structure-digits)
            (not (nil? (re-find #"^\d*$" structure-digits)))
            (<= 20 (count structure-digits))
            (>= 31 (count structure-digits))]}
     (let [{:keys [barcode service routing] :as imb-data}
            (split-structure-digits structure-digits)]
       (if (contains? origin-service-types service)
         (encode barcode
                 service
                 (:customer-number imb-data)
                 routing)
         (encode barcode
                 service
                 (get-in imb-data [:6-digit-mailer :mailer-id])
                 (get-in imb-data [:6-digit-mailer :serial-number])
                 routing))))
  ([barcode service customer-number routing]
     {:pre [(every? string? [barcode service customer-number routing])
            (not (nil? (re-find #"^\d[0-4]$" barcode)))
            (= (count service) 3)
            (= (count customer-number) 15)
            (#{0 5 9 11} (count routing))]}
     (encode-binary-data
      (binary-encode barcode service customer-number routing)))
  ([barcode service mailer serial-number routing]
     {:pre [(every? string? [barcode service mailer serial-number routing])
            (not (nil? (re-find #"^\d[0-4]$" barcode)))
            (= (count service) 3)
            (or (and (= (count mailer) 6)
                     (= (count serial-number) 9))
                (and (= (count mailer) 9)
                     (= (count serial-number) 6)))
            (#{0 5 9 11} (count routing))]}
     (encode-binary-data
      (binary-encode barcode service mailer serial-number routing))))
