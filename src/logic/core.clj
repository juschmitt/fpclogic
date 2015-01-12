(ns logic.core

(:refer-clojure :exclude [==])
(:use [clojure.core.logic])
  (:use [clojure.core.logic.pldb])
)

(db-rel father Father Child)
(db-rel mother Mother Child)




(def dbf (db [father 'Vito 'Michael]
                              [father 'Vito 'Sonny]
                              [father 'Vito 'Fredo]
                              [father 'Michael 'Anthony]
                              [father 'Michael 'Mary]
                              [father 'Sonny 'Vicent]
                              [father 'Sonny 'Francesca]
                              [father 'Sonny 'Kathryn]
                              [father 'Sonny 'Frank]
                              [father 'Sonny 'Santino]))
 
(def dbm (db [mother 'Carmela 'Michael]
                              [mother 'Carmela 'Sonny]
                              [mother 'Carmela 'Fredo]
                              [mother 'Kay 'Mary]
                              [mother 'Kay 'Anthony]
                              [mother 'Sandra 'Francesca]
                              [mother 'Sandra 'Kathryn]
                              [mother 'Sandra 'Frank]
                              [mother 'Sandra 'Santino]))

(defn children [p] 
  ""
  (with-dbs [dbf dbm] (run* [q] (conde ((father p q)) ((mother p q)))))
  )

(defn parentse [c] 
  ""
  (with-dbs [dbm dbf] (run* [q] (conde ((father q c)) ((mother q c)))))
  )

(defn fathero [c]
  ""
  (with-db dbf (run* [q] (father q c)))
  )

(defn mothero [c]
  ""
  (with-db dbm (run* [q] (mother  q c)))
  )

(defn grandchild [c]
  ""
  (with-dbs [dbf] (run* [q] (fresh [x y] (father c x) (father x y) (== q y))))
  )
