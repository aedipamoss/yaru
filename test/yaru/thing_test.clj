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

(deftest test-get-thing
  (let [result (things/insert-thing-return-keys db {:title "my thing"
                                                    :color "red"
                                                    :priority "high"})
        id ((keyword "last_insert_rowid()") result)
        thing (things/thing-by-id db {:id id})
        json-resp (generate-string thing)]
    (is (= 200 (:status (app (request :get (str "/things/" id))))))
    (is (= json-resp (:body (app (request :get (str "/things/" id))))))))

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

(deftest test-create-thing
  (let [thing {:title "my new thing"
               :color "green"
               :priority "low"}
        response (app (-> (request :post "/things")
                          (json-body thing)))
        last-thing (last (things/all-things db))
        json-resp (parse-string (:body response))]
    (is (= 200 (:status response)))
    (is (= "green" (get json-resp "color")))))

(deftest test-update-thing
  (let [result (things/insert-thing-return-keys db {:title "a typo"
                                                    :color "red"
                                                    :priority "high"})
        id ((keyword "last_insert_rowid()") result)
        thing (things/thing-by-id db {:id id})
        fixed (conj thing {:title "fixed"})
        response (app (-> (request :put (str "/things/" id))
                          (json-body fixed)))
        updated (things/thing-by-id db {:id id})
        parsed (parse-string (:body response))
        expected (parse-string (generate-string updated))]
    (is (= 200 (:status response)))
    (is (= expected parsed))))

(deftest test-delete-thing
  (let [result (things/insert-thing-return-keys db {:title "delete me"
                                                    :color "blue"
                                                    :priority "low"})
        id ((keyword "last_insert_rowid()") result)
        thing (things/thing-by-id db {:id id})
        response (app (request :delete (str "/things/" id)))
        last-thing (last (things/all-things db))]
    (is (= nil (things/thing-by-id db {:id id})))
    (is (= 200 (:status response)))
    (is (= id (:body response)))))
