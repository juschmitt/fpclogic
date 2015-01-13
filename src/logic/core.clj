;
; Vortrag 14.01.2015
; Julian Schmitt, Chris Weber
;
; Clojure 'core.logic'
; 
; Logische Programmierung
;
;



(ns logic.core	
	(:refer-clojure :exclude [==])
	(:use [clojure.core.logic])
  	(:use [clojure.core.logic.pldb]))



; Definieren einer "Datenbank" - Relation

(db-rel father Father Child)
(db-rel mother Mother Child)


; Die oben definierten Relationen können jetzt verwendet werden
; um mit 'db' eine Repräsentation im Speicher zu erzeugen

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


; Zugriffe auf die Datenbank über 'with-db' oder 'with-dbs'
;

(defn children [p] 
  "Returns a list of children of p"
  (with-dbs [dbf dbm] 
	(run* [q] 
		(conde ((father p q)) ((mother p q))))))

(children "Vito")
; ()

(children 'Vito)
; (Michael Sonny Fredo)


(defn parentse [c] 
  "Returns a list of parents of c"
  (with-dbs [dbm dbf] 
	(run* [q] 
		(conde ((father q c)) ((mother q c))))))

(parentse 'Vito)
; ()

(parentse 'Anthony)
; (Michael Kay)


(defn fathero [c]
  "Returns the father of c"
  (with-db dbf 
	(run* [q] 
		(father q c))))

(fathero 'Anthony)
; (Michael)


(defn mothero [c]
  "Returns the mother of c"
  (with-db dbm (run* [q] (mother  q c)))
  )

(mothero 'Anthony)
; (Kay)


(defn grandchild [c]
  "Returns the grandchild(s) of c"
  (with-dbs [dbf] (run* [q] (fresh [x y] (father c x) (father x y) (== q y))))
  )

(grandchild 'Vito)
; (Francesca Santino Frank Mary Kathryn Vicent Anthony)
