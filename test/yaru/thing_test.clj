(ns yaru.thing-test
  (:require [cheshire.core :refer [generate-string parse-string]]
            [clojure.test :refer :all]
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
        json-resp (generate-string thing)]
    (is (= 200 (:status (app (request :get (str "/thing/" id))))))
    (is (= json-resp (:body (app (request :get (str "/thing/" id))))))))

(deftest test-all-things
  (let [result (things/insert-thing-return-keys db {:title "another thing"
                                                    :color "yellow"
                                                    :priority "medium"})
        id ((keyword "last_insert_rowid()") result)
        thing (things/thing-by-id db {:id id})
        response (app (request :get "/things"))
        parsed (parse-string (:body response))
        expected (parse-string (generate-string [thing]))]
    (is (= 200 (:status response)))
    (is (= expected parsed))))

(deftest test-create-thing-routes
  (let [thing {:title "my new thing"
               :color "green"
               :priority "low"}
        response (app (-> (request :post "/thing")
                          (json-body thing)))
        last-thing (last (things/all-things db))
        json-resp (parse-string (:body response))]
    (is (= 200 (:status response)))
    (is (= "green" (get json-resp "color")))))
