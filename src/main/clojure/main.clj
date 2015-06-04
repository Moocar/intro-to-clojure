(ns main
  (:require jokes)
  (:gen-class))

(defn -main [& args]
  (-> args
      first
      Integer/parseInt
      jokes/get-n
      jokes/print-results))
