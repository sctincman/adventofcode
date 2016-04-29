(import 'java.security.MessageDigest)

(defn md5 [s]
  (apply str
         (map (partial format "%02x")
              (.digest (doto (MessageDigest/getInstance "MD5")
                         .reset
                         (.update (.getBytes s)))))))

(def salt "bgvyzdsv")

(count (take-while (fn [hash]
                     (not= "00000" (subs hash 0 5)))
                   (map md5 (map (fn [num] (clojure.string/join [salt num])) (map str (range))))))

(count (take-while (fn [hash]
                     (not= "000000" (subs hash 0 6)))
                   (map md5 (map (fn [num] (clojure.string/join [salt num])) (map str (range))))))
