(defproject com.walmartlabs/intro-to-clojure-talk "0.0.1-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.7.0-beta2"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [org.clojure/data.json "0.2.6"]

                 [http-kit "2.1.18"]

                 [enlive "1.1.5"]

                 ;; Logging
                 [org.slf4j/log4j-over-slf4j "1.7.12"]
                 [org.slf4j/jcl-over-slf4j "1.7.12"]
                 [org.slf4j/jul-to-slf4j "1.7.12"]
                 [org.slf4j/slf4j-api "1.7.12"]
                 [ch.qos.logback/logback-classic "1.0.13"]]
  :main com.walmartlabs.main
  :source-paths ["src/main/clojure"])
