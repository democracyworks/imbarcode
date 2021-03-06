(ns imbarcode.big-integer
  "Wraps goog.math.Integer operations to provide
   a common, Clojure-esque interface for dealing with arbitrary
   precision integers in ClojureScript."
  (:refer-clojure :exclude [+ * inc quot rem int zero?])
  (:import [goog.math Integer]))

(defn valid-string? [string]
  (re-matches #"^\d*$" string))

(def from-string Integer.fromString)

(defn ->goog-big-int [x]
  (cond
   (= (type x) js/Number) (Integer.fromNumber x)
   (= (type x) js/String) (Integer.fromString x)
   (= (type x) Integer) x))

(defn + [& args]
  (reduce (fn [a b] (.add a b)) Integer.ZERO
          (map ->goog-big-int args)))

(defn * [& args]
  (reduce (fn [a b] (.multiply a b)) Integer.ONE
          (map ->goog-big-int args)))

(defn inc [x]
  (-> x
      ->goog-big-int
      (.add Integer.ONE)))

(defn quot [x y]
  (.divide (->goog-big-int x)
           (->goog-big-int y)))

(defn rem [x y]
  (.modulo (->goog-big-int x)
           (->goog-big-int y)))

(defn int [x] (.toInt (->goog-big-int x)))

(defn zero? [x] (.isZero (->goog-big-int x)))
