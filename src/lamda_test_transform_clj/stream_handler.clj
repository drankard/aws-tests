(ns lamda-test-transform-clj.stream-handler
  (:gen-class
    :implements [com.amazonaws.services.lambda.runtime.RequestStreamHandler])
  (:require [clojure.data.json :as json]
            [clojure-csv.core :as csv]

            [clojure.string :as s]
            [clojure.java.io :as io]
            [clojure.pprint :refer [pprint]])
  (:import (com.amazonaws.services.s3 AmazonS3Client)
           (com.amazonaws.services.s3.model GetObjectRequest)
           (com.amazonaws.auth EnvironmentVariableCredentialsProvider)))

(defn get-csv [event]
  (let [bn (get-in event [:Records 0 :s3 :bucket :name])
        k (get-in event [:Records 0 :s3 :object :key])
        c (AmazonS3Client. (EnvironmentVariableCredentialsProvider.))
        r (GetObjectRequest. bn k)
        _ (prn bn k)
        o (.getObject c r)

        ]
    (prn o)
    )

  )
(defn parse-input [event]

  )

(defn handle-event [event]
  (println event)
  (println (get-in event [:records 0 :s3   :bucket :name]))
  (println (get-in event [:records 0 :s3 :object :key]))
  {:who-done-it (get-in event [:records 0 :request-parameters :source-ip-address])
   :bucket-owner (get-in event [:records 0 :s3 :bucket :owner-identity :principal-id])})

(defn key->keyword [key-string]
  (-> key-string
      (s/replace #"([a-z])([A-Z])" "$1-$2")
      (s/replace #"([A-Z]+)([A-Z])" "$1-$2")
      (s/lower-case)
      (keyword)))

(defn -handleRequest [this is os context]
  (let [w (io/writer os)]
    (-> (json/read (io/reader is) :key-fn keyword)
        (handle-event)
        (json/write w))
    (.flush w)))