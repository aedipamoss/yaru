(ns yaru.core
  (:require [compojure.core :refer [defroutes routes GET POST PUT DELETE]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.json :refer [wrap-json-response wrap-json-params]]
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
   (POST "/things" {{thing :thing} :params} (things/post thing))
   (PUT "/things/:id" {{id :id} :route-params {thing :thing} :params}  (things/put id thing))))

(def app
  (-> handler
      (wrap-default-charset "utf-8")
      wrap-keyword-params
      wrap-json-params
      wrap-json-response))

(defn -main []
  (run-jetty app {:port 3000}))
