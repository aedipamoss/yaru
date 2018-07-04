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
   (DELETE "/things/:id" [id] (things/delete-thing id))
   (GET "/things" [] (things/all-things))
   (GET "/things/:id" [id] (things/get-thing id))
   (POST "/things" request (things/create-thing request))
   (PUT "/things/:id" request (things/update-thing request))))

(def app
  (-> handler
      wrap-params
      (wrap-default-charset "utf-8")
      (wrap-json-body {:keywords? true})
      wrap-json-response))

(defn -main []
  (run-jetty app {:port 3000}))
