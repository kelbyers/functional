(defn div-by?
  [num div]
  (zero? (rem num div)))

(div-by? 4 3)

(defn is-prime?
  [candidate [a-prime & primes]]
  (if (nil? a-prime)
    (do
      ; (println "no more to check")
      true)
    (do
      ; (println "checking C:" candidate "p:" a-prime)
      (if (div-by? candidate a-prime)
        (do
          ; (println "no prime")
          false)
        (do
          ; (println "checking rest:" primes)
          (recur candidate primes))))))

(defn next-prime
  [primes]
  (if (empty? primes)
    2
    (loop [candidate (inc (peek primes))]
      ; (println "Checking" candidate)
      (if (is-prime? candidate primes)
        candidate
        (recur (inc candidate))))))

(next-prime [2 3])
(is-prime? 22 [2 3 5 7 11])

(== 5 5)

; (defn factor
;   ([num] (factor num [] []))
;   ([num factors primes]
;    (println "factoring N:" num "F:" factors "P:" primes)
;    (loop [prime (next-prime primes)
;           primes (conj primes prime)]
;      (println "p:" prime "a:" primes)
;      (if (== prime num)
;        (do
;          (println "is prime:" num)
;          [num])
;        (if (> prime num)
;          (do
;            (println "All found - n:" num "p:" prime "f:" factors)
;            factors)
;          (if (div-by? num prime)
;            (do
;              (println "new factor:" prime "existing:" factors)
;              (into (conj factors prime) (factor (/ num prime))))
;            (do
;              (println "check next n:" num "f:" factors "a:" augmented)
;              (factor num factors augmented))))))))

(defn l-factor
  [num]
  (loop [prime (next-prime [])
         primes []]
    (if (== num prime)
      [prime]
      (if (div-by? num prime)
        (into [prime] (l-factor (/ num prime)))
        (let [primes (conj primes prime)
              next (next-prime primes)]
          (recur next primes))))))

(l-factor 837)

(factor 12)

(into [2] (conj [] 3))

(defn primes-to
  [limit]
  (loop [next (next-prime [])
         primes []]
    (if (> next limit)
      primes
      (let [primes (conj primes next)]
        (recur (next-prime primes) primes)))))

(primes-to 20)

(l-factor 600851475143)
