(def directions (filter (fn [char] (or (= char \() (= char \)))) (slurp "day01.txt")))
(def floors (reductions + (map (fn [char] (if (= char \() 1 -1)) directions)))

(println (last floors))
(println (count (take-while (partial <= -1) floors)))

