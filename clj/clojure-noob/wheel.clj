(defn wheel-ring [ring size]
  (let [xn    (* ring size)
        start (inc xn)
        end   (+ start size)]
    (range start end)))

(wheel-ring 1 6)

(defn sieve-ring [primes ring]
  (loop [[prime & primes] primes
         ring ring]
    (println "P':" prime "pl:" primes "R:" ring)
    (if (nil? prime)
      ring
      (recur
        primes
        (filter #(pos? (mod % prime)) ring)))))

(defn ring0 [primes size]
  (let [ring (sieve-ring primes (wheel-ring 0 size))
        ring-1 (filter #(not= 1 %) ring)]
    [(apply min ring-1) (apply sorted-set (into ring-1 primes))]))

(defn make-wheel [primes]
  (let [size              (apply * primes)
        [num-rings ring0] (ring0 primes size)]
    (loop [wheel [ring0]]
      (if (= (count wheel) num-rings)
        wheel
        (let [ring-num  (count wheel)
              ring      (wheel-ring ring-num size)]
          (recur (conj wheel (apply sorted-set (sieve-ring primes ring)))))))))

(filter #(pos? (mod % 2)) (sorted-set 1 2 3 4 5 6))

(wheel-ring 0 6)

(sieve-ring [2 3] (wheel-ring 0 6))

(apply sorted-set (ring0 [2 3] 6))
(ring0 [2 3] 6)

(make-wheel [2 3])
