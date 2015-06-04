(ns json
  (:require [clojure.data.json :as json]))

(defn read-str [string]
  (json/read-str string :key-fn keyword))
