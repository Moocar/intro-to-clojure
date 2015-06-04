(ns jokes
  (:require [clojure.core.async :as async
             :refer [>!! <!! go <! >!]]
            [clojure.pprint :refer [pprint]]
            json
            http))

(def url
  "http://api.icndb.com/jokes/random")

;; &limitTo=[nerdy]&escape=javascript
(def options
  {:query-params {:limitTo "[nerdy]"
                  :escape "javascript"}})

(defn response->joke
  "Takes an http response map and extracts the joke as a string"
  [response]
  (-> response
      :body
      json/read-str
      :value
      :joke))

(defn wait-and-sort
  "Given a channel, takes n items from it, converts each into a joke,
  sorts by the length of each joke and return them as a vector"
  [ch n]
  (->> ch
       (async/take n)
       (async/into [])
       <!!
       (map response->joke)
       (sort-by count)))

(defn get-n
  "Make n non-blocking requests to the chuck norris joke service and
  returns them as a vector sorted by length"
  [n]
  (let [ch (async/chan n)]
    (dotimes [_ n]
      (http/get url options ch))
    (wait-and-sort ch n)))

(defn print-results
  "Prints each joke on a new line"
  [jokes]
  (doseq [joke jokes]
    (println joke)))
