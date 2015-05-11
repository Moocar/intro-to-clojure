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
// src/com/walmartlabs/com.walmartlabs.Main.java
package com.walmartlabs;

public class com.walmartlabs.Main {
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

# Scalars
```clojure
1
42
67.5
(type 67.5)
-90
(type 345879623458976234052634587263459872345982347653245)
0x1A
10.7e-3
22/7
\a
"my string"
```

# Collections

```clojure
'(8 9)
[4]
#{7}
{"my string" 42}

(type [])
(instance? java.util.List [])

[9 "blah" 2]
{"my string" 42
 22/7 4}
#{7 8 7}
;; IllegalArgumentException Duplicate key: 7
[3 "blah" {"my key" #{\c [5]}}]
```

# Function

```clojure
(count [7 8 9])
3
(min 4 5)
4
(max 4 5)
5
(doc count)
-------------------------
clojure.core/count
([coll])
  Returns the number of items in the collection. (count nil) returns
  0.  Also works on strings, arrays, and Java Collections and Maps

(+ 1 2)
;; 1 + 2

(+ 1 2 3 4 5 6)
;; 1 + 2 + 3 + 4 + 5 + 6

(+ (- (/ 10 2) 3) 10)
```
