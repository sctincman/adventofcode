(require '[clojure.string :as str])

(def wire-ns (create-ns 'wires))

(defn u16-bit [bit-fn & more]
  (let [u16s (map (fn [num] (bit-and 0xffff (unchecked-int num))) more)]
    (bit-and 0xffff (apply bit-fn u16s))))

(defn u16-rshift [num n]
  (unsigned-bit-shift-right (bit-and 0xffff (unchecked-int num)) n))

(defn u16-lshift [num n]
  (bit-and 0xffff (bit-shift-left (unchecked-int num) n)))

(defn eval-var [term]
  (if (number? term)
    term
    ((var-get (find-var (symbol (str/join ["wires/" term])))))))

(defn parse-wire-string [wire-string]
  (let [the-vec (mapv read-string (str/split wire-string #" "))]
    
    (intern wire-ns (symbol (last the-vec))
            (cond
              (re-find #"NOT" wire-string)
              (memoize (fn []
                         (u16-bit bit-not
                                  (eval-var (second the-vec)))))
              
              (re-find #"OR" wire-string)
              (memoize (fn []
                         (u16-bit bit-or
                                  (eval-var (the-vec 0))
                                  (eval-var (the-vec 2)))))
              
              (re-find #"AND" wire-string)
              (memoize (fn []
                         (u16-bit bit-and
                                  (eval-var (the-vec 0))
                                  (eval-var (the-vec 2)))))
              
              (re-find #"LSHIFT" wire-string)
              (memoize (fn []
                         (u16-lshift
                          (eval-var (the-vec 0))
                          (eval-var (the-vec 2)))))
              
              (re-find #"RSHIFT" wire-string)
              (memoize (fn []
                         (u16-rshift
                          (eval-var (the-vec 0))
                          (eval-var (the-vec 2)))))
              
              (= 3 (count the-vec))
              (memoize (fn [] (eval-var (the-vec 0))))))))

(def old-a (with-open [rdr (clojure.java.io/reader "day07.txt")]
             (doall (map parse-wire-string (line-seq rdr)))
             (eval-var "a")))

(def new-a (with-open [rdr (clojure.java.io/reader "day07.txt")]
             (doall (map parse-wire-string (line-seq rdr)))
             (intern wire-ns 'b (fn [] old-a))
             (eval-var "a")))

(println "Old a: " old-a)
(println "New a: " new-a)
