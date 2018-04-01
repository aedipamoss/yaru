(ns yaru.db.things
  (:require [hugsql.core :as hugsql]
            [hugsql.adapter.clojure-java-jdbc :as cj-adapter]))

(def adapter-settings
  {:adapter (cj-adapter/hugsql-adapter-clojure-java-jdbc)})

(hugsql/def-db-fns "yaru/db/sql/things.sql" adapter-settings)
(hugsql/def-sqlvec-fns "yaru/db/sql/things.sql" adapter-settings)
