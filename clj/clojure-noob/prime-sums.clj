(defn is-prime?
  [num primes]
  (not (some zero? (map (partial mod num) primes))))

(defn next-prime
  ([] 2)
  ([primes]
   (loop [candidate (inc (peek primes))]
     (if (is-prime? candidate primes)
       candidate
       (recur (inc candidate))))))

(next-prime)
(next-prime '(2 3))

(defn prime-seq
  ([] (prime-seq [] (next-prime)))
  ([primes next]
   (let [next-primes (conj primes next)]
     (lazy-seq
       (cons next (prime-seq next-primes (next-prime next-primes)))))))

(defn p-sum-below
  [num]
  (loop [sum 0
         count 0
         [p-num & primes] (prime-seq)]
    ; (println "N:" num "S:" sum "p:" p-num)
    (if (zero? (mod count 10000))
      (println "N:" num "S:" sum "p:" p-num))
    (if (< p-num num)
      (do
        (recur (+ sum p-num) (inc count) primes))
      sum)))

(p-sum-below 2000000)

(defn sieve [s]
  (cons (first s)
        (lazy-seq (sieve (filter #(not= 0 (mod % (first s)))
                                 (rest s))))))

(defn s-prime-seq
  (lazy-cat '(2) (sieve (iterate #(+ 2 %) 3))))

(time (take 1000 (sieve (iterate inc 2))))
(time (take 1000 (prime-seq)))

(let [j (time (println "starting"))
      ll (time (take 1000 (prime-seq)))
      sl (time (take 1000 (sieve (iterate inc 2))))]
  (= sl ll))
