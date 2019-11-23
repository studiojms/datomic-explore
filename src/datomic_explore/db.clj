(ns datomic-explore.db
  (:require [datomic.api :as d]
            [datomic-explore.model :as model]))

(def db-uri "datomic:free://localhost:4334/super-store?password=12345")

(defn create-database! []
  (d/create-database db-uri))

(defn get-connection []
  (d/connect db-uri))

(defn delete-database! []
  (d/delete-database db-uri))

(def schema [{:db/ident       :product/name
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "Product's name"}
             {:db/ident       :product/slug
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/one
              :db/doc         "Product's slug (to access prod via http)"}
             {:db/ident       :product/price
              :db/valueType   :db.type/bigdec
              :db/cardinality :db.cardinality/one
              :db/doc         "Product's cardinality"}])


(defn create-schema! [conn]
  (d/transact conn schema))

(defn add-product! [conn product]
  (d/transact conn product))