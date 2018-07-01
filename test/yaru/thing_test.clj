(ns yaru.thing-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [yaru.core :refer :all]
            [yaru.db :refer [db]]
            [yaru.db.things :as things]))

(defn setup []
  (things/create-things-table db))

(defn teardown []
  (things/drop-things-table db))

(defn each-fixture [f]
  (setup)
  (f)
  (teardown))

(use-fixtures :each each-fixture)

(deftest test-thing-routes
  (let [result (things/insert-thing-return-keys db {:title "my thing"
                                                    :color "red"
                                                    :priority "high"})
        id ((keyword "last_insert_rowid()") result)
        thing (things/thing-by-id db {:id id})
        json-resp (cheshire.core/generate-string thing)]
    (is (= 200 (:status (app (request :get (str "/thing/" id))))))
    (is (= json-resp (:body (app (request :get (str "/thing/" id))))))))

(deftest test-create-thing-routes
  (let [thing {:title "my new thing"
               :color "green"
               :priority "low"}
        response (app (-> (request :post "/thing")
                          (json-body thing)))
        last-thing (last (things/all-things db))
        json-response (cheshire.core/parse-string (:body response))]
    (is (= 200 (:status response)))
    (is (= "green" (get json-response "color")))))
