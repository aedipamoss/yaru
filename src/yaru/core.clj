(ns yaru.core
  (:require [cheshire.core :refer :all]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.util.response :refer [response]]
            [yaru.thing :as things])
  (:use ring.adapter.jetty
        ring.middleware.params
        ring.middleware.default-charset))

(defroutes handler
  (routes
   (GET "/thing/:id" [id] (things/get-thing id))
   (POST "/thing" request (things/create-thing request))))

(def app
  (-> handler
      wrap-params
      (wrap-default-charset "utf-8")
      (wrap-json-body {:keywords? true})
      wrap-json-response))

(defn -main []
  (run-jetty app {:port 3000}))
