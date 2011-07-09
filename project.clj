(defproject simple-war "1.0.0"
  :description "Simple Web Application"
  :dependencies [[org.clojure/clojure "1.2.1"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [compojure "0.6.4"]
                 [ring/ring-servlet "0.3.8"]]
  :dev-dependencies [[swank-clojure "1.4.0-SNAPSHOT"]
                     [ring-serve "0.1.1"]
                     [uk.org.alienscience/leiningen-war "0.0.13"]]
  :namespaces [simple-war.core])