(defn update-lights [light-map command]
  (let [keys (for [x (range (command 1)
                            (command 3))
                   y (range (command 2)
                            (command 4))]
               [x y])]
    (cond (zero? (compare (command 0) "toggle"))
          (reduce (fn [acc key]
                    (update acc key not)) ; update passes nil if key doesn't exist, and (not nil) => true
                  light-map keys)
          
          (zero? (compare (command 0) "turn on"))
          (reduce (fn [acc key]
                    (assoc acc key true))
                  light-map keys)
          
          (zero? (compare (command 0) "turn off"))
          (reduce (fn [acc key]
                    (assoc acc key false))
                  light-map keys)
          :else light-map)))

(defn update-lights! [light-map command]
  (let [keys (for [x (range (command 1)
                            (command 3))
                   y (range (command 2)
                            (command 4))]
               [x y])]
    (persistent!
     (cond (zero? (compare (command 0) "toggle"))
           (reduce (fn [acc key]
                     (assoc! acc key (not (acc key))))
                   (transient light-map) keys)
           
           (zero? (compare (command 0) "turn on"))
           (reduce (fn [acc key]
                     (assoc! acc key true))
                   (transient light-map) keys)
           
           (zero? (compare (command 0) "turn off"))
           (reduce (fn [acc key]
                     (assoc! acc key false))
                   (transient light-map) keys)
           
           :else (transient light-map)))))

(defn brighten-lights! [light-map command]
  (let [keys (for [x (range (command 1)
                            (command 3))
                   y (range (command 2)
                            (command 4))]
               [x y])]
    (persistent!
     (cond (zero? (compare (command 0) "toggle"))
           (reduce (fn [acc key]
                     (assoc! acc key
                             (let [val (acc key)]
                               (if (number? val)
                                 (+ val 2)
                                 2))))
                   (transient light-map) keys)
           
           (zero? (compare (command 0) "turn on"))
           (reduce (fn [acc key]
                     (assoc! acc key
                             (let [val (acc key)]
                               (if (number? val)
                                 (inc val)
                                 1))))
                   (transient light-map) keys)
           
           (zero? (compare (command 0) "turn off"))
           (reduce (fn [acc key]
                     (assoc! acc key
                             (let [val (acc key)]
                               (if (and (number? val)
                                        (pos? val))
                                 (dec val)
                                 0))))
                   (transient light-map) keys)
           
           :else (transient light-map)))))

(defn parse-command-string [command]
  [(command 1)
   (read-string (command 2))
   (read-string (command 3))
   (inc (read-string (command 4)))   ; range works exclusively, and we need to include the final point
   (inc (read-string (command 5)))])

(let [commands (map parse-command-string 
                    (re-seq #"(toggle|turn off|turn on) ([0-9]+),([0-9]+) through ([0-9]+),([0-9]+)"
                            (slurp "day06.txt")))]
  (println (count (filter (fn [v] v) (vals (reduce update-lights! {} commands)))))
  (println (reduce + 0 (vals (reduce brighten-lights! {} commands)))))

