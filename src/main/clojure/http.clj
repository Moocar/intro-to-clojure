(ns http
  (:require [clojure.core.async :as async
             :refer [>!!]]
            [org.httpkit.client :as http])
  (:refer-clojure :exclude [get]))

(defn get
  ([url options]
   (get url options (async/chan)))
  ([url options ch]
   (http/get url options (fn [r] (>!! ch r)))
   ch))
