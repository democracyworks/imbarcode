(defproject turbovote.imbarcode "0.1.0-SNAPSHOT"
  :description "Generate USPS Intelligent Mail Barcodes"
  :url "https://github.com/turbovote/imbarcode"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2138"]]
  :plugins [[lein-cljsbuild "1.0.1"]
            [com.keminglabs/cljx "0.3.2"]
            [com.cemerick/clojurescript.test "0.2.1"]]
  :source-paths ["src/clj" "src/cljs"]
  :cljsbuild {
              :crossovers [turbovote.imbarcode]
              :crossover-path "src/crossover"
              :builds [{:id "dev"
                        :source-paths ["src/cljs" "src/crossover"]
                        :compiler {:output-to "resources/public/javascript/main-dev.js"
                                   :output-dir "resources/public/javascript/dev/"
                                   :optimizations :none
                                   :pretty-print true
                                   :source-map true}}
                       {:id "prod"
                        :source-paths ["src/cljs" "src/crossover"]
                        :compiler {:output-to "resources/public/javascript/main.js"
                                   :output-dir "resources/public/javascript/prod/"
                                   :optimizations :advanced}}
                       {:id "test"
                        :source-paths ["src/cljs"
                                       "src/crossover"
                                       "target/generated/test/cljs"]
                        :compiler {:output-to "target/cljs/testable.js"
                                   :output-dir "target/cljs/"
                                   :optimizations :whitespace}}]
              :test-commands {"unit-tests" ["phantomjs" :runner
                                            "target/cljs/testable.js"]}}
  :cljx {:builds [{:source-paths ["test/cljx"]
                   :output-path "target/generated/test/clj"
                   :rules :clj}
                  {:source-paths ["test/cljx"]
                   :output-path "target/generated/test/cljs"
                   :rules :cljs}]}
  :test-paths ["target/generated/test/clj"]
  :hooks [cljx.hooks]
  :aliases {"cleantest" ["do" "clean," "test," "cljsbuild" "test"]})
