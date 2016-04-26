(require '[clojure.string :as str])

(with-open [rdr (clojure.java.io/reader "day02.txt")]
  (let [dims (map (fn [line] (map read-string (str/split line #"x"))) (line-seq rdr))]
    (println "Total area: "
             (reduce + (map (fn [[x y z]]
                              (let [areas [(* x y) (* x z) (* y z)]]
                                (reduce + (conj (map (partial * 2) areas) (apply min areas)))))
                            dims)))
    (println "Total ribbon: "
             (reduce + (map (fn [[x y z]]
                              (+ (* x y z) (* 2 (- (+ x y z) (max x y z)))))
                            dims)))))
