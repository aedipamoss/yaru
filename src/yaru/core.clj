(ns yaru.core
  (:require [cheshire.core :refer :all]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-body]]
            [ring.util.response :refer [response]])
  (:use ring.adapter.jetty
        ring.middleware.params
        ring.middleware.default-charset
        yaru.thing))

(defn hello [name]
  (response (str "Hello " name)))

(defn show-thing [id]
  (response (str (get-thing id))))

(defn create-thing [request]
  (let [request (wrap-json-body request)]
    (prn request)
    (response "created thing.")))

(defroutes handler
  (routes
   (GET "/hello/:name" [name] (hello name))
   (GET "/thing/:id" [id] (show-thing id))
   (POST "/thing" request (create-thing request))))

(def app
  (-> handler
      (wrap-default-charset "utf-8")
      (wrap-params)))

(defn -main []
  (run-jetty app {:port 3000}))
