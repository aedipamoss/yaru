(ns yaru.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route])
  (:use ring.adapter.jetty
        ring.middleware.params
        ring.middleware.default-charset
        yaru.thing))

(defn hello [name]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (str "Hello " name)})

(defn show-thing [id]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (str (get-thing id))})

(defn create-thing []
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (

(defroutes handler
  (routes
   (GET "/hello/:name" [name] (hello name))
   (GET "/thing/:id" [id] (show-thing id))))

(def app
  (-> handler
      (wrap-default-charset "utf-8")
      (wrap-params)))

(defn -main []
  (run-jetty app {:port 3000}))
