(defproject yaru "0.1.0-SNAPSHOT"
  :description "やりたい事"
  :url "https://github.com/aedipamoss/yaru"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [cheshire "5.8.0"]
                 [compojure "1.6.0"]
                 [ring/ring-core "1.6.2"]
                 [ring/ring-jetty-adapter "1.6.2"]
                 [ring/ring-json "0.4.0"]
                 [ring/ring-headers "0.3.0"]
                 [com.layerware/hugsql "0.4.8"]
                 [org.xerial/sqlite-jdbc "3.21.0.1"]
                 [org.clojure/java.jdbc "0.7.5"]]
  :main yaru.core)
