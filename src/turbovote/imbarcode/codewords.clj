(ns turbovote.imbarcode.codewords)

(defn data->codewords-base [data]
  (let [codeword-map {:J (int (rem data 636))}]
    (loop [data (quot data 636)
           codeword-map codeword-map
            codewords [:I :H :G :F :E :D :C :B]]
      (if (empty? codewords)
        (let [codeword-map (assoc codeword-map :A (int data))]
          (vec (map codeword-map [:A :B :C :D :E :F :G :H :I :J])))
        (recur (quot data 1365)
               (assoc codeword-map (first codewords) (int (rem data 1365)))
               (rest codewords))))))

(defn orient-codeword-j [codewords]
  (assoc codewords 9 (-> codewords (nth 9) (* 2))))

(defn align-codeword-a [codewords fcs]
  (if (zero? (bit-and fcs 1024))
    codewords
    (assoc codewords 0 (-> codewords first (+ 659)))))

(defn data->codewords [data fcs]
  (-> data
      data->codewords-base
      orient-codeword-j
      (align-codeword-a fcs)))
