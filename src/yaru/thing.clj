(ns yaru.thing
  (:require [yaru.db :refer [db]]
            [yaru.db.things :as db.things]))

(defn get-thing [thing-id]
  (let [thing (db.things/thing-by-id db {:id thing-id})]
    {:status 200 :body thing}))

(defn create-thing [request]
  (let [result (db.things/insert-thing-return-keys db (:body request))
        id ((keyword "last_insert_rowid()") result)
        thing (db.things/thing-by-id db {:id id})]
    {:status 200 :body thing}))
