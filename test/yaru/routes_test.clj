(ns yaru.routes-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [yaru.core :refer :all]))

(deftest test-default-routes
  (is (= 200 (:status (app (request :get "/hello/test")))))
  (is (= "Hello test"
         (:body (app (request :get "/hello/test"))))))

