(defproject democracyworks/imbarcode "1.0.3-SNAPSHOT"
  :description "Generate USPS Intelligent Mail Barcodes"
  :url "https://github.com/democracyworks/imbarcode"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.229"]
                 [org.clojure/test.check "0.9.0"]]
  :plugins [[lein-cljsbuild "1.1.3"]
            [lein-doo "0.1.7"]]
  :profiles {:dev {:dependencies [[doo "0.1.7"]
                                  [org.clojure/data.csv "0.1.3"]]}
             :test {:resource-paths ["test-resources"]}
             ; Needed for the alias `clj-test`
             :without-aliases {:aliases ^:replace {}}}
  :source-paths ["src/cljc" "src/clj"]
  :test-paths ["test/cljc"]
  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src/cljs" "src/cljc"]
                        :compiler {:output-to "resources/public/javascript/main-dev.js"
                                   :output-dir "resources/public/javascript/dev/"
                                   :optimizations :none
                                   :pretty-print true
                                   :source-map true}}
                       {:id "prod"
                        :source-paths ["src/cljs" "src/cljc"]
                        :compiler {:output-to "resources/public/javascript/main.js"
                                   :output-dir "resources/public/javascript/prod/"
                                   :optimizations :advanced}}
                       {:id "test"
                        :source-paths ["src/cljs" "src/cljc" "test/cljs" "test/cljc"]
                        :compiler {:output-to "target/cljs/testable.js"
                                   :output-dir "target/cljs/"
                                   :optimizations :whitespace
                                   :pretty-print true
                                   :main imbarcode.test-runner}}]}
  :aliases {"test" ["do" "test," "cljs-test"]
            "clj-test" ["with-profile" "+without-aliases" "test"]
            "cljs-test" ["doo" "phantom" "test" "once"]}
  :deploy-repositories [["releases" :clojars]])
