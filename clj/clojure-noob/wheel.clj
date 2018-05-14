(use 'clojure.set)

(defn sievefn [[primes, candidates]]
  (if (empty? candidates) [primes, candidates]
    (let [prime (first candidates)
          end (inc (apply max candidates))
          multiples (range prime end prime)
          newprimes (conj primes prime)
          newcandidates (difference candidates multiples)]
      [newprimes, newcandidates])))

(def to20 [(sorted-set) (sorted-set 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20)])
(sievefn to20)

(sievefn (sievefn to20))

(take 10 (iterate sievefn to20))

(defn sieverecur [primes candset]
  (if (empty? candset) primes
    (let [prime (first candset)
          end (inc (apply max candset))]
      (recur (conj primes prime) (difference candset (range prime end prime))))))

(defn sieverecur1
  ([primes candset]
   (sieverecur1 primes candset (inc (apply max candset))))
  ([primes candset end]
   (if (empty? candset)
     primes
     (let [prime (first candset)]
       (recur
        (conj primes prime)
        (clojure.set/difference candset (range prime end prime))
        end)))))

(sieverecur (sorted-set) (apply sorted-set (range 2 100)))

(into (sorted-set) (range 2 20))

(take 5 #{27 69 91 28 60 92 29 61})
(take 5 (apply sorted-set #{27 69 91 28 60 92 29 61}))
(apply sorted-set #{27 69 91 28 60 92 29 61})
(apply sorted-set (range 20))

(sieverecur1 (sorted-set) (apply sorted-set (range 2 100)))

(def lt100 (sieverecur1 (sorted-set) (apply sorted-set (range 2 100))))

(loop [primes (sieverecur1 (sorted-set) (apply sorted-set (range 2 100)))]
  (println "PL:" primes)
  (if (not (empty? primes))
    (println (first primes))
    (recur (rest primes))))

(loop [[prime & remaining] (vec lt100)]
  (println "P:" prime "R:" remaining)
  (if (not (empty? remaining))
    (recur remaining)))

(defn infinite-sieve-fn [[testset int]]
  (let [pair (first testset)
        [multiple prime] pair]
    (cond
      ; (= int multiple) (list testset (inc int))
      (= int multiple) (list testset (+ 2 int))
      (> int multiple) (list (conj (disj testset pair) [(+ multiple prime) prime]) int)
      (< int multiple) (list (conj testset [int int]) int))))

(def sieves (iterate infinite-sieve-fn [(sorted-set [2 2]) 3]))
(infinite-sieve-fn [(sorted-set [2,2]) 3])

(take 5 sieves)

(nth sieves 20)
(sort (map second (first (nth sieves 200))))

(def sieveprimes (map (fn[x] (sort (map second (first x)))) sieves))

(nth sieveprimes 1000)
(take 10 (reverse (nth sieveprimes 10000)))
