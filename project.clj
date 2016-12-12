(defproject lamda-test-transform-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [com.amazonaws/aws-lambda-java-core "1.1.0"]
                 [com.amazonaws/aws-lambda-java-events "1.3.0"]
                 [com.amazonaws/aws-java-sdk-s3 "1.11.66"]
                 [org.clojure/data.json "0.2.6"]
                 [clojure-csv/clojure-csv "2.0.1"]]
  :aot :all)
