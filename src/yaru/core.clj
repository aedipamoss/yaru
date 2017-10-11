(ns yaru.core
  (:require [compojure.core :refer :all]
            [compojure.route :as route])
  (:use ring.adapter.jetty
        ring.middleware.params
        ring.middleware.default-charset))

(defn hello [name]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (str "Hello " name)})

(defroutes handler
  (routes
   (GET "/hello/:name" [name] (hello name))))

(def app
  (-> handler
      (wrap-default-charset "utf-8")
      (wrap-params)))

(defn -main []
  (run-jetty app {:port 3000}))
