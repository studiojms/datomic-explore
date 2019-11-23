(ns datomic-explore.core
  (:require [datomic-explore.db :as db]
            [datomic-explore.model :as model]))


(let [conn (db/get-connection)]
  (db/create-schema! conn)
  (db/add-product! conn [(model/new-product "Computer" "/computer" 3200.33M)]))
