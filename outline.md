# Outline
- What is Clojure?
- Clojure in eReceipts
- Intro to the Language
- Concurrency with core.async
- Recommended resources

# What is Clojure?
- Functional, Dynamic Lisp on the JVM
- Used at all these companies. (screenshot of
  http://clojure.org/Companies)
- 8 years old

## Where can it be used?
Everywhere that java can. It is just source code that compiles to
byte code in a jar file. Screenshot of deployment process. Any java
library can be used.

## Superpowers
- Functional
- Immutability/Persistent data structures
- Concurrency
- Data structures
- Data transformations
- Repl driven development
- LISP
- Macros
- Java interop
- Fun!
- Simple
- Backwards compatible

# Clojure in eReceipts

## What is eReceipts?
- Platform. Realtime stream of all in-store transactions
- Distributed system that handles 1000 transactions per second on BF
- Database of all Walmart/Sams transactions
- Database of all customer/receipt associations
- Production for 2 years now

## Projects/Customers
- Returns at Walmart stores and Sam's clubs
- eReceipts on the Walmart app
- Debit Card reader phone number entry eReceipts/eCoupons
- Savings Catcher
- One Hour Guarantee
- Vudu Instawatch
- Registry project
- Walmart/Sam's Checkin
- Walmart/Sams iPhone an Android push notifications
- Walmart Site 2 Store

All in Clojure!

# Hello World

At the artifact level. We start with source code, we run it through a
compilation process, and produces a jar file.

```java
// src/com/walmartlabs/Main.java
package com.walmartlabs;

public class Main {
  public static void main [String[] args] {
    System.out.println("Hello world!");
  }
}
```

```bash
mvn package
java -jar target/uberjar.jar
```

```clojure
;; src/com/walmartlabs/main.clj
(ns com.walmartlabs.main
  (:gen-class))

(defn -main [& args]
  (println "Hello world!"))
```

```bash
lein package
java -jar target/uberjar.jar
```

# The REPL

```bash
lein repl
```
