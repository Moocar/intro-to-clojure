(ns walkthrough
  (:require [clojure.repl
             :refer [doc source]])
  (:import (java.util Vector
                      HashMap
                      Date)
           (java.awt Point)))

;; Scalars

(def a (+ 1 2))
(+ 1 2 3 4 5 6)
(def s "my string")
(class s)
(type 123784598763498576239485762348956)
22/7
(type 22/7)
(class clojure.lang.Ratio)





;;; Functions

(class "foo")
(doc class)
(+ 1 2)
(doc +)
(min 4 5)
(max 4 5)

(defn foo [a b]
  (+ 10 a b))

(foo 42 142)

(instance? Runnable foo)
(instance? Callable foo)

(fn [] (println "moo"))
#(println "moo")

(.start
 (Thread. (fn [] (println "Hi There"))))
(.start
 (Thread. #(println "Hi There")))
(future (println "Hi There"))





;; Datastructures
'(1 2 3)
[5 6 7]
(instance? java.util.List [5 6 7])
#{1 2 3}
(instance? java.util.Set #{1})
#{1}
{"foo" 1
 "bar" 2}
(instance? java.util.Map {"foo" 1})
[1 "foo" 8 \c]
{"foo" [1 2 #{8 ["bar"]}]}

(def l '(1 2 3 4 5))




;; Higher Order function


(def a [1 2 3 4])
(defn add10 [x] (+ 10 x))
(add10 1)
(def b (map add10 a))

(map #(+ % 10) [1 2 3 4])

(doc even?)
(def a [1 2 3 4])
(filter even? a)

(map inc (filter even? (map add10 a)))
(->> [1 2 3 4]
     (map add10)
     (filter even?)
     (map inc))
