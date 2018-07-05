(ns yaru.core
  (:require [compojure.core :refer [defroutes routes GET POST PUT DELETE]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.util.response :refer [response]]
            [yaru.thing :as things])
  (:use ring.adapter.jetty
        ring.middleware.params
        ring.middleware.default-charset))

(defroutes handler
  (routes
   (DELETE "/things/:id" [id] (things/delete id))
   (GET "/things" [] (things/list))
   (GET "/things/:id" [id] (things/get id))
   (POST "/things" request (things/post request))
   (PUT "/things/:id" request (things/put request))))

(def app
  (-> handler
      wrap-params
      (wrap-default-charset "utf-8")
      (wrap-json-body {:keywords? true})
      wrap-json-response))

(defn -main []
  (run-jetty app {:port 3000}))
