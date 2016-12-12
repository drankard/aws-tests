(ns utils
  (:require [clojure.data.json :as json]
            [lamda-test-transform-clj.stream-handler :refer :all]))

(def e (key->keyword (json/read-json (slurp "resources/s3-put.json") :true)))