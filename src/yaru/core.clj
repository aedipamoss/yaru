(ns yaru.core
  (:use ring.middleware.params
        ring.middleware.default-charset))

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (str "Hello " ((:params request) "name"))})

(def app
  (-> handler
      (wrap-default-charset "utf-8")
      (wrap-params)))

