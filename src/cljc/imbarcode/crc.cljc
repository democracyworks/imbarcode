(ns imbarcode.crc
  "Handles generation of 11 bit CRC on binary data.
   See section 3.2.2 of the IMb spec."
  (:require [clojure.string :as s]
            [imbarcode.big-integer :as bi]))

(defn pad [data n]
  (concat data (repeat n 0)))

(defn front-pad-to [data n]
  (concat (repeat (- n (count data)) 0) data))

(defn int->binvector [i]
  (loop [v '()
         n i]
    (if (bi/zero? n)
      v
      (recur (conj v (bi/int (bi/rem n 2)))
             (bi/quot n 2)))))

(defn xor [data p]
  (concat (map bit-xor (take (count p) data) p)
          (drop (count p) data)))

(defn crc [input poly-div]
  (let [crc-len (-> poly-div count dec)
        padded (pad input crc-len)]
    (loop [result (xor padded (repeat crc-len 1))]
      (cond (= (count result) crc-len)
            result
            (zero? (first result))
            (recur (rest result))
            :else
            (recur (xor result poly-div))))))

(defn IMb-fcs
  "Returns the 11 bit IMb frame check sequence
   represented as a vector of 0s and 1s.
   binary-data should be a vector of 0s and 1s representing
   the calculated binary data"
  [binary-data]
  (let [fcs-11 (crc (drop 2 (front-pad-to (int->binvector binary-data) 104))
                    (int->binvector 0xf35))]
    (bi/int (bi/from-string (s/join fcs-11) 2))))
