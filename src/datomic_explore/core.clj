(ns datomic-explore.core
  (:require [datomic-explore.db :as db]
            [datomic.api :as d]
            [datomic-explore.model :as model]))

(db/delete-database!)

(db/create-database!)

(let [conn (db/get-connection)]
  (db/create-schema! conn)
  (db/add-product! conn [(model/new-product "Computer" "/computer" 3200.33M)])
  (db/add-product! conn [(model/new-product "Test" "/test" 233.2M)]))

(def db (d/db (db/get-connection)))
(def db-old (d/as-of (d/db (db/get-connection)) #inst "2019-12-23T22:11:11.100"))

(db/list-products db)

(db/list-products-by-slug db "/computer")

(db/list-slugs db)

(db/list-prices db)

(db/find-product-by-price db 500)

(let [conn (db/get-connection)]
  ;(db/create-schema! conn)
  (d/transact conn [[:db/add 17592186045418 :product/keyword "computer"]
                    [:db/add 17592186045420 :product/keyword "desktop"]]))
