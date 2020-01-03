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
              :db/doc         "Product's cardinality"}
             {:db/ident       :product/keyword
              :db/valueType   :db.type/string
              :db/cardinality :db.cardinality/many
              :db/doc         "Product's keyword"}])


(defn create-schema! [conn]
  (d/transact conn schema))

(defn add-product! [conn product]
  (d/transact conn product))

;(defn list-products [db]
;  (d/q '[:find (pull ?entity [:product/name :product/price :product/slug])
;         :where [?entity :product/name]] db))

(defn list-products [db]
  (d/q '[:find (pull ?entity [*])
         :where [?entity :product/name]] db))

(defn list-products-by-slug [db slug]
  (d/q '[:find ?entity
         :in $ ?slug-param
         :where [?entity :product/slug ?slug-param]]
       db slug))

(defn list-slugs [db]
  (d/q '[:find ?slug
         :where [_ :product/slug ?slug]]
       db))

(defn list-prices [db]
  (d/q '[:find ?name ?price
         ;:keys product/name product/price
         :where [?p :product/price ?price]
                [?p :product/name ?name]]
       db))

(defn find-product-by-price [db minimum-price]
  (d/q '[:find ?name ?price
         ;:keys product/name product/price
         :in $ ?min-price-param
         :where [?p :product/price ?price]
                [(>= ?price ?min-price-param)]
                [?p :product/name ?name]]
       db minimum-price))
