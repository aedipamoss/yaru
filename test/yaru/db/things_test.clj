(ns yaru.db.things-test
  (:require [clojure.test :refer :all]
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

(deftest test-create-things
  (things/insert-thing db {:title "omg"
                           :color "red"
                           :priority "high"})
  (is (= "omg" (:title (last (things/all-things db))))))

(deftest test-create-and-return-things
  (let [result (things/insert-thing-return-keys db {:title "my new thing"
                                                    :color "blue"
                                                    :priority "low"})
        id ((keyword "last_insert_rowid()") result)]
    (is (= "blue" (:color (things/thing-by-id db {:id id}))))))

(deftest test-all-things
  (things/insert-thing db {:title "creating a thing"
                           :color "yellow"
                           :priority "medium"})
  (is (> (count (things/all-things db)) 0)))

(deftest test-all-things-vector
  (things/insert-thing db {:title "creating a thing"
                           :color "yellow"
                           :priority "medium"})
  (let [result (things/all-things db)]
    (is (= "yellow" (:color (first result))))))

(deftest test-update-thing-by-id
  (let [result (things/insert-thing-return-keys db {:title "some typo"
                                                    :color "yellow"
                                                    :priority "medium"})
        id ((keyword "last_insert_rowid()") result)
        thing (things/thing-by-id db {:id id})
        updated (conj thing {:title "fixed typo"})]
    (things/update-thing-by-id db updated)
    (is (= "fixed typo" (:title (things/thing-by-id db {:id id}))))))
