(ns turbovote.big-integer
  "Wraps clojure.lang.BigInt to provide a common interface
   for interop with javascript."
  (:refer-clojure :exclude [+ * inc quot rem int zero?]))

(defn valid-string? [string]
  (re-matches #"\A\d*\z" string))

(defn from-string
  ([string] (bigint (BigInteger. string)))
  ([string radix] (bigint (BigInteger. string radix))))

(def + +')
(def * *')
(def inc clojure.core/inc)
(def quot clojure.core/quot)
(def rem clojure.core/rem)
(def int clojure.core/int)
(def zero? clojure.core/zero?)
