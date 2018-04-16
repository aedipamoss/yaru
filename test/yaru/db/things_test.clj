(ns yaru.db.things-test
  (:require [clojure.test :refer :all]
            [yaru.db :refer [db]]
            [yaru.db.things :as things]))

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
  (is (> (count (things/all-things db)) 0)))

(deftest test-update-thing-by-id
  (let [result (things/insert-thing-return-keys db {:title "some typo"
                                                    :color "yellow"
                                                    :priority "medium"})
        id ((keyword "last_insert_rowid()") result)
        thing (things/thing-by-id db {:id id})
        updated (conj thing {:title "fixed typo"})]
    (things/update-thing-by-id db updated)
    (is (= "fixed typo" (:title (things/thing-by-id db {:id id}))))))
