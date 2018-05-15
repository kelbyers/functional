(str 9)
(vector 1 2 3 4 5)
(list 1 2 3 4 5)
(hash-map :a 1 :b 2)
(hash-set 1 2 3 2 1 2 3 4)
(defn cent [x] (+ 100 x))
(cent 23)

(defn inc-maker
  "Create a custom incrementor"
  [inc-by]
  #(+ % inc-by))
(def inc3 (inc-maker 3))
(inc3 7)

(defn dec-maker
  [dec-by]
  #(- % dec-by))
(def dec9 (dec-maker 9))
(dec9 11)

(defn mapset
  [f map-seq]
  (set (map f map-seq)))

(mapset inc [1 1 2 2])

(defn matching-alien-part [part from to]
  (let [re-from (re-pattern (format "^%s-" from))]
   {:name (clojure.string/replace (:name part) re-from (format "%s-" to))
    :size (:size part)}))

(matching-alien-part {:name "ventral-eye" :size 3} "ventral" "dorsal")
(symetrize-alien-part1 {:name "ventral-eye" :size 3} "ventral" '("right" "left"))

(defn symetrize-alien-part1 [part from to-seq]
  (loop [[to & remaining] to-seq
         new-parts [part]]
    (if (nil? to)
      (set new-parts)
      (recur remaining
             (conj new-parts (matching-alien-part part from to))))))

(defn symmetrize-alien-parts
  [asym-body-parts]
  (let [locations '("right" "center" "dorsal" "ventral")]
    (loop [[part & asym-body-parts] asym-body-parts
           final-body-parts []]
      (println "P:" part)
      (if (nil? asym-body-parts)
        final-body-parts
        (recur
         asym-body-parts
         (into final-body-parts (symetrize-alien-part1 part "left" locations)))))))

(defn symmetrize-general-parts
  [asym-body-parts from & locations]
  (loop [[part & asym-body-parts] asym-body-parts
         final-body-parts []]
    (println "P:" part)
    (if (nil? asym-body-parts)
      final-body-parts
      (recur
       asym-body-parts
       (into final-body-parts (symetrize-alien-part1 part from locations))))))

(def asym-alien-body-parts [{:name "head" :size 3}
                            {:name "left-eye" :size 1}
                            {:name "left-ear" :size 1}
                            {:name "mouth" :size 1}
                            {:name "nose" :size 1}
                            {:name "neck" :size 2}
                            {:name "left-shoulder" :size 3}
                            {:name "left-upper-arm" :size 3}
                            {:name "chest" :size 10}
                            {:name "back" :size 10}
                            {:name "left-forearm" :size 3}
                            {:name "abdomen" :size 6}
                            {:name "left-kidney" :size 1}
                            {:name "left-hand" :size 2}
                            {:name "left-knee" :size 2}
                            {:name "left-thigh" :size 4}
                            {:name "left-lower-leg" :size 3}
                            {:name "left-achilles" :size 1}
                            {:name "left-foot" :size 2}])

(symmetrize-alien-parts asym-alien-body-parts)

(symmetrize-general-parts asym-alien-body-parts "left" "right" "center")
