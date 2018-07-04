(ns yaru.thing
  (:require [yaru.db :refer [db]]
            [yaru.db.things :as db.things]))

(defn get-thing [thing-id]
  (let [thing (db.things/thing-by-id db {:id thing-id})]
    {:status 200 :body thing}))

(defn all-things []
  (let [things (db.things/all-things db)]
    {:status 200 :body things}))

(defn create-thing [request]
  (let [result (db.things/insert-thing-return-keys db (:body request))
        id ((keyword "last_insert_rowid()") result)
        thing (db.things/thing-by-id db {:id id})]
    {:status 200 :body thing}))

(defn update-thing [request]
  (let [result (db.things/update-thing-by-id db (:body request))
        thing (db.things/thing-by-id db {:id result})]
    {:status 200 :body thing}))

(defn delete-thing [id]
  (let [result (db.things/delete-thing-by-id db {:id id})]
    {:status 200 :body result}))
