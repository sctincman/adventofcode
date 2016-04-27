(def directions (map (fn [char]
                       (case char
                         \^ [0 1]
                         \v [0 -1]
                         \> [1 0]
                         \< [-1 0]
                         [0 0]))
                     (slurp "day03.txt")))

(count (distinct (reductions (fn [acc next] (map + acc next)) directions)))

(count (distinct (concat
                  (reductions (fn [acc next] (map + acc next)) (take-nth 2 directions))
                  (reductions (fn [acc next] (map + acc next)) (take-nth 2 (rest directions))))))
