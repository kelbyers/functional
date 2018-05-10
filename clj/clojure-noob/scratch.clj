
(def asym-hobbit-body-parts [{:name "head" :size 3}
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

(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(defn symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (loop [remaining-asym-parts asym-hobbit-body-parts
         final-body-parts []]
    (if (empty? remaining-asym-parts)
      final-body-parts
      (let [[part & remaining] remaining-asym-parts]
        (recur remaining
               (into final-body-parts
                     (set [part (matching-part part)])))))))

(matching-part {:name "left-foot" :size 2})
(matching-part {:name "left-lower-leg" :size 3})
(reduce + [1 2 3 4])
(reduce + 15 [1 2 3 4])

(defn my-reduce
  ([f initial coll]
   (loop [result initial
          remaining coll]
     (if (empty? remaining)
       result
       (recur (f result (first remaining)) (rest remaining)))))
  ([f [head & tail]]
   (my-reduce f head tail)))

(defn better-symmetrize-body-parts
  "Expects a seq of maps that have a :name and :size"
  [asym-body-parts]
  (reduce (fn [final-body-parts part]
            (into final-body-parts (set [part (matching-part part)])))
          []
          asym-body-parts))

(defn hit
  [asym-body-parts]
  (let [sym-parts (better-symmetrize-body-parts asym-body-parts)
        body-part-size-sum (reduce + (map :size sym-parts))
        target (rand body-part-size-sum)]
    (loop [[part & remaining] sym-parts
           accumulated-size (:size part)]
      (if (> accumulated-size target)
        part
        (recur remaining (+ accumulated-size (:size (first remaining))))))))

(hit asym-hobbit-body-parts)

(= 3 5)
(= 0 0)

(defn even-div?
  [num div]
  (= 0 (rem num div)))

(defn m35?
  [num]
  (or (even-div? num 3) (even-div? num 5)))

(defn add-m35
  [num]
  (if (m35? num)
    num
    0))

(defn sum-35
  [limit]
  (loop [sum 0
         num 1]
    (if (>= num limit)
      sum
      (recur (+ sum (add-m35 num)) (inc num)))))

(sum-35 1000)

(defn add-even
  [sum num]
  (if (even? num)
    (+ sum num)
    sum))

(add-even 5 3)

(< 10 5)

(defn e-fib-sum-lim
  [limit]
  (let [start-a 1
        start-b 2]
    (if (< limit start-b)
      0
      (loop [a start-a
             b start-b
             sum 0]
        (if (< limit b)
          sum
          (recur b (+ a b) (add-even sum b)))))))

(e-fib-sum-lim 4000000)

(defn test-prime?
  [candidate testor]
  (not= 0 (rem candidate testor)))

(test-prime? 3 2)

(defn test-primes
  [primes candidate]
  ; (println "C:" candidate "P:" primes)
  (if (nil? (peek primes))
    true
    (let [[num & rest] primes]
      ; (println "Checking C:" candidate "N:" num)
      (if (test-prime? candidate num)
        (test-primes (vec rest) candidate)
        false))))

(test-primes [2 3] 5)

(defn next-prime
  ([primes] (next-prime primes (inc (peek primes))))
  ([primes candidate]
   ; (println "P:" primes "C:" candidate)
   (if (test-primes primes candidate)
     (conj primes candidate)
     (next-prime primes (inc candidate)))))

(next-prime [2 3])

(defn prime-factor?
  [num prime]
  (zero? (rem num prime)))

(prime-factor? 4 3)

(defn factor-check-found
  [num prime found]
  (if (prime-factor? num prime)
    (conj (set found) prime)
    found))

(factor-check-found 10 5 #{2})

(defn factors
  ([num] (factors num [2] []))
  ([num primes factors]
   (println "N:" num "P:" primes "F:" factors)
   (let [maxfactor (peek primes)]
     (println "M:" maxfactor)
     (if (> (* maxfactor 2) num)
       factors
       (loop [[p & rest] primes
              found (set factors)]
         (println "p:" p "r:" rest "f:" found)
         (if (nil? p)
           (let [np (next-prime primes)]
             (println "NP:" np)
             (factors num np (vec found)))
           (do
             (println "f-check p:" p "n:" num)
             (recur rest (factor-check-found num p found)))))))))

(factors 4)
