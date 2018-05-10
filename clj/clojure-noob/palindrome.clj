(str 99)
(get "abcdef" 2)
(count "abcdef")
(/ 3 2)
(use 'clojure.set)
(defn round
  [num]
  (int (+ 0.5 num)))

(round 1.5)

(defn palindome-number?
  [num]
  (let [f-num (str num)
        r-num (reverse f-num)]
    (loop [[f-dig & f-num] f-num
           [r-dig & r-num] r-num]
      (if (nil? f-dig)
        true
        (if (not= f-dig r-dig)
          false
          (recur f-num r-num))))))

(defn pal-prod
  [a b]
  (let [p (* a b)]
    (if (palindome-number? p)
      [p]
      [])))

(palindome-number? 99)
(palindome-number? 9019)
(pal-prod 91 99)

(defn pal-prod-rng-b
  [a min-b max-b]
  (loop [b min-b
         pals #{}]
    (if (> b max-b)
      (vec pals)
      (recur (inc b) (union pals (pal-prod a b))))))

(pal-prod-rng-b 91 99 99)

(pal-prod-rng-b 11 10 99)

(defn pal-prod-rng
  [min-a max-a min-b max-b]
  (loop [a min-a
         pals #{}]
    (if (> a max-a)
      (vec (sort pals))
      (recur (inc a) (into pals (pal-prod-rng-b a min-b max-b))))))

(pal-prod-rng 11 11 10 99)

(= (pal-prod-rng-b 11 10 99) (pal-prod-rng 11 11 10 99))

(pal-prod-rng 10 99 10 99)

(apply max (pal-prod-rng 10 99 10 99))

(apply max (pal-prod-rng 100 999 100 999))
