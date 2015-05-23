(ns com.walmartlabs.chucknorris
  (:require [clojure.core.async :as async :refer [>!! <!!]]
            [clojure.data.json :as json]
            [clojure.string :as string]
            [clojure.pprint :refer [pprint]]
            [org.httpkit.client :as http]
            [net.cgrand.enlive-html :as html]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Scrape!!

(def board-url "http://corporate.walmart.com/our-story/leadership/board-of-directors/")

(def fetch-url
  (memoize
   (fn [url]
     (html/html-resource (java.net.URL. url)))))

(defn first-and-last-name
  "Takes a full name and returns [first-name last-name]"
  [string]
  (let [tokens (string/split string #"\s")]
    [(first tokens) (last tokens)]))

(def element->names
  "Takes an html element, and converts it to [first-name last-name]"
  (comp first-and-last-name
        string/trim
        first
        :content))

(defn find-directors
  "Takes the walmart board of directors html and returns [first-name
  last-name]"
  [html]
  (let [elements (html/select html [:h4.execName :a])]
    (map element->names elements)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Chuck Norris joke service

(def chuck-norris-url "http://api.icndb.com/jokes/random")
;; http://api.icndb.com/jokes/random?firstName=Rich&lastName=Hickey&limitTo=[nerdy]&escape=javascript

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
  (http/get chuck-norris-url
            {:query-params {"limitTo" "[nerdy]"
                            "escape" "javascript"
                            "firstName" first-name
                            "lastName" last-name}}
            #(>!! ch %)))

(defn get-jokes
  "Makes `joke-count` requests to the ChuckNorris joke service in
  parallel, and returns the jokes in order of length"
  [names]
  (let [ch (async/chan (count names) (map parse-joke))]
    (doseq [[first-name last-name] names]
      (request first-name last-name ch))
    (->> ch
         (async/take (count names))
         (async/into [])
         (<!!)
         (sort-by count))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; To Run it:

(defn run []
  (let [names (find-directors (fetch-url board-url))]
    (doseq [joke (get-jokes names)]
      (println joke))))
