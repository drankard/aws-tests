(ns lamda-test-transform-clj.transform
  (:gen-class
    :methods [^:static [handler [String] String]]))


(defn -handler [s]
  (str "Hello " s "!"))