(ns turbovote.imbarcode.bars
  (:require [clojure.string :as s :only [join]]))

(def A 0)
(def B 1)
(def C 2)
(def D 3)
(def E 4)
(def F 5)
(def G 6)
(def H 7)
(def I 8)
(def J 9)

(def bar-rules
  [{:desc [H 2] :asc [E 3]}
   {:desc [B 10] :asc [A 0]}
   {:desc [J 12] :asc [C 8]}
   {:desc [F 5] :asc [G 11]}
   {:desc [I 9] :asc [D 1]}
   {:desc [A 1] :asc [F 12]}
   {:desc [C 5] :asc [B 8]}
   {:desc [E 4] :asc [J 11]}
   {:desc [G 3] :asc [I 10]}
   {:desc [D 9] :asc [H 6]}
   {:desc [F 11] :asc [B 4]}
   {:desc [I 5] :asc [C 12]}
   {:desc [J 10] :asc [A 2]}
   {:desc [H 1] :asc [G 7]}
   {:desc [D 6] :asc [E 9]}
   {:desc [A 3] :asc [I 6]}
   {:desc [G 4] :asc [C 7]}
   {:desc [B 1] :asc [J 9]}
   {:desc [H 10] :asc [F 2]}
   {:desc [E 0] :asc [D 8]}
   {:desc [G 2] :asc [A 4]}
   {:desc [I 11] :asc [B 0]}
   {:desc [J 8] :asc [D 12]}
   {:desc [C 6] :asc [H 7]}
   {:desc [F 1] :asc [E 10]}
   {:desc [B 12] :asc [G 9]}
   {:desc [H 3] :asc [I 0]}
   {:desc [F 8] :asc [J 7]}
   {:desc [E 6] :asc [C 10]}
   {:desc [D 4] :asc [A 5]}
   {:desc [I 4] :asc [F 7]}
   {:desc [H 11] :asc [B 9]}
   {:desc [G 0] :asc [J 6]}
   {:desc [A 6] :asc [E 8]}
   {:desc [C 1] :asc [D 2]}
   {:desc [F 9] :asc [I 12]}
   {:desc [E 11] :asc [G 1]}
   {:desc [J 5] :asc [H 4]}
   {:desc [D 3] :asc [B 2]}
   {:desc [A 7] :asc [C 0]}
   {:desc [B 3] :asc [E 1]}
   {:desc [G 10] :asc [D 5]}
   {:desc [I 7] :asc [J 4]}
   {:desc [C 11] :asc [F 6]}
   {:desc [A 8] :asc [H 12]}
   {:desc [E 2] :asc [I 1]}
   {:desc [F 10] :asc [D 0]}
   {:desc [J 3] :asc [A 9]}
   {:desc [G 5] :asc [C 4]}
   {:desc [H 8] :asc [B 7]}
   {:desc [F 0] :asc [E 5]}
   {:desc [C 3] :asc [A 10]}
   {:desc [G 12] :asc [J 2]}
   {:desc [D 11] :asc [B 6]}
   {:desc [I 8] :asc [H 9]}
   {:desc [F 4] :asc [A 11]}
   {:desc [B 5] :asc [C 2]}
   {:desc [J 1] :asc [E 12]}
   {:desc [I 3] :asc [G 6]}
   {:desc [H 0] :asc [D 7]}
   {:desc [E 7] :asc [H 5]}
   {:desc [A 12] :asc [B 11]}
   {:desc [C 9] :asc [J 0]}
   {:desc [G 8] :asc [F 3]}
   {:desc [D 10] :asc [I 2]}])

(def bar-vector->char
  {[true true] \F
   [true false] \D
   [false true] \A
   [false false] \T})

(defn extender? [characters [char-idx bit-idx]]
  (not (zero? (bit-and (characters char-idx)
                       (bit-shift-left 1 bit-idx)))))

(defn bar [bar-idx characters]
  (let [{:keys [desc asc]} (bar-rules bar-idx)]
    (bar-vector->char
     [(extender? characters desc) (extender? characters asc)])))

(defn bars [characters]
  (s/join (map #(bar % characters) (range 65))))
