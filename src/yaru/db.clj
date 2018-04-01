(ns yaru.db
  (:require [hugsql.core :as hugsql]))

(def db
  {:subprotocol "sqlite"
   :subname (str (System/getProperty "java.io.tmpdir")
                 "/yaru.db")})
