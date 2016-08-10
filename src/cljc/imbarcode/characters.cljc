(ns imbarcode.characters
  "Handles conversion from codewords to characters.
   See section 3.2.5 of the IMb spec."
  (:require [imbarcode.character-conversion :refer [codeword->character]]))

(defn codewords->characters-base [codewords]
  (map codeword->character codewords))

(defn adjust-character [character fcs-bit]
  (if (zero? fcs-bit)
    character
    (bit-xor character 0x1fff)))

(defn adjust-characters [characters fcs]
  (map (fn [char bit-idx]
         (adjust-character char (bit-and fcs (bit-shift-left 1 bit-idx))))
       characters (range 10)))

(defn codewords->characters [codewords fcs]
  (-> codewords
      codewords->characters-base
      (adjust-characters fcs)
      vec))
