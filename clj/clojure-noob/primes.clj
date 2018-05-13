(defn prime-to
  [n]
  (reduce
    (fn [primes number]
      (if (some zero? (map (partial mod number) primes))
        primes
        (conj primes number)))
    [2]
    (take (- n 2) (iterate inc 3))))

(prime-to 13)

(defn is-prime?
  [num primes]
  (not (some zero? (map (partial mod num) primes))))

(is-prime? 5 [2 3])
(is-prime? 5 '(2 3))

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

(take 6 (prime-seq))
(last (take 10001 (prime-seq)))
