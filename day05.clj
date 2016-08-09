(require '[clojure.string :as str])

(defn is-nice? [string]
  (and (>= (count (re-seq #"[aeiou]" string))
           3)
       (> (count (re-seq #"(.)\1" string))
          0)
       (= (count (re-seq #"ab|cd|pq|xy" string))
          0)))

(defn is-nicer? [string]
  (and (> (count (re-seq #"(..).*\1" string))
           0)
       (> (count (re-seq #"(.).\1" string))
          0)))

(with-open [rdr (clojure.java.io/reader "day05.txt")]
  (let [the-strings (line-seq rdr)
        nice-strings (filter is-nice? the-strings)
        nicer-strings (filter is-nicer? the-strings)]
    (println "There are" (count nice-strings) "nice strings.")
    (println "There are" (count nicer-strings) "nicer strings.")))
