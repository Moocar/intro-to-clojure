(ns com.walmartlabs.imdb
  (:require [clojure.core.async :as async :refer [>!! <!!]]
            [clojure.data.json :as json]
            [clojure.pprint :refer [pprint]]
            [org.httpkit.client :as http]))

(def url "http://api.icndb.com/jokes/random")

(defn parse-joke
  "Takes a raw http response and extracts the joke out of it"
  [response]
  (-> response
      :body
      (json/read-str :key-fn keyword)
      :value
      :joke))

(defn request
  "Makes a request to the chuck norris joke service using first an
  last name, and puts the result onto ch"
  [first-name last-name ch]
  (http/get url
            {:query-params {"limitTo" "[nerdy]"
                            "escape" "javascript"
                            "firstName" first-name
                            "lastName" last-name}}
            (partial >!! ch)))

(defn get-jokes
  "Makes `joke-count` requests to the ChuckNorris joke service in
  parallel, and returns the jokes in order of length"
  [joke-count first-name last-name]
  (let [ch (async/chan joke-count (map parse-joke))]
    (dotimes [i joke-count]
      (request first-name last-name ch))
    (->> ch
         (async/take joke-count)
         (async/into [])
         (<!!)
         (sort-by count))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; To Run it:

(defn run []
  (doseq [joke (get-jokes 10 "Chuck" "Norris")]
    (println joke)))
