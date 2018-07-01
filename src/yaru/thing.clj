(ns yaru.thing
  (:require [yaru.db :refer [db]]
            [yaru.db.things :as db.things]))

(defn get-thing [thing-id]
  (let [thing (db.things/thing-by-id db {:id thing-id})]
    (cheshire.core/generate-string thing)))

(defn create-thing [request]
  (let [result (db.things/insert-thing-return-keys db (:body request))
        id ((keyword "last_insert_rowid()") result)
        thing (db.things/thing-by-id db {:id id})]
    (cheshire.core/generate-string thing)))
