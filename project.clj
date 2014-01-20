(defproject turbovote.imbarcode "0.1.0-SNAPSHOT"
  :description "Generate USPS Intelligent Mail Barcodes"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2138"]]
  :plugins [[lein-cljsbuild "1.0.1"]]
  :source-paths ["src/clj" "src/cljs"]
  :cljsbuild {
              :crossovers [turbovote.imbarcode turbovote.imbarcode-test]
              :crossover-path "src/crossover"
              :builds [{:id "dev"
                        :source-paths ["src/cljs" "src/crossover" "test"]
                        :compiler {:output-to "resources/public/javascript/main-dev.js"
                                   :output-dir "resources/public/javascript/"
                                   :optimizations :none
                                   :pretty-print true
                                   :source-map true}}]})
