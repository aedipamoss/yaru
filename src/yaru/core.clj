(ns yaru.core
  (:use ring.adapter.jetty
        ring.middleware.params
        ring.middleware.default-charset))

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (str "Hello " ((:params request) "name"))})

(def app
  (-> handler
      (wrap-default-charset "utf-8")
      (wrap-params)))

(defn -main []
  (run-jetty app {:port 3000}))
