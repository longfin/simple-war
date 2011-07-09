(ns simple-war.core
  (:use [compojure.core]
        [ring.util.servlet])
  (:require [compojure.route :as route]
            [compojure.handler :as handler])
  (:gen-class
   :extends javax.servlet.http.HttpServlet))

(defn factorial [n]
  (loop [c n
         result 1]
    (if (= c 0)
      result
      (recur (- c 1) (* result c)))))

(defn fibonacci [n]
  (loop [a 1
         b 1
         c n]
    (cond (= c 1) a
          (= c 2) b
          :else (recur b (+ a b) (- c 1)))))

(def context-path (atom nil))

(defn- get-context-path
  ([] @context-path)
  ([servlet-req]
     (if (nil? @context-path)
       (reset! context-path (.getContextPath servlet-req)))
     @context-path))

(defn wrap-context
  [handler]
  (fn [request]
    (if-let [servlet-req (:servlet-request request)]
      (let [context (get-context-path servlet-req)
            uri (:uri request)]
        (if (.startsWith uri context)
          (handler (assoc request :uri
                          (.substring uri (.length context))))
          (handler request)))
      (handler request))))

(defroutes main-routes
  (GET "/" [] "<h1>Korea Lisp 2011</h1>")
  (GET "/factorial/:n" [n]
       (str "<h1>" (factorial (bigint n)) "</h1>"))
  (GET "/fibonacci/:n" [n]
       (str "<h1>" (fibonacci (bigint n)) "</h1>"))
  (route/not-found "Page not found"))

(def app
  (-> main-routes
      wrap-context))

(defservice app)
