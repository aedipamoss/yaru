(ns yaru.thing
  (:require [yaru.db :refer [db]]
            [yaru.db.things :as db.things])
  (:refer-clojure :exclude (list get delete)))

(defn get [id]
  (let [thing (db.things/thing-by-id db {:id id})]
    {:status 200 :body thing}))

(defn list []
  (let [things (db.things/all-things db)]
    {:status 200 :body things}))

(defn post [thing]
  (let [result (db.things/insert-thing-return-keys db thing)
        id ((keyword "last_insert_rowid()") result)
        thing (db.things/thing-by-id db {:id id})]
    {:status 200 :body thing}))

(defn put [id thing]
  (let [result (db.things/update-thing-by-id db thing)
        thing (db.things/thing-by-id db {:id id})]
    {:status 200 :body thing}))

(defn delete [id]
  (let [thing (db.things/thing-by-id db {:id id})
        result (db.things/delete-thing-by-id db {:id id})]
    {:status 200 :body thing}))
