(defn div-by?
  [num div]
  (zero? (rem num div)))

(defn seq-div-by?
  [div my-seq]
  (if (first my-seq)
    (if (div-by? (first my-seq) div)
      true
      (recur div (rest my-seq)))
    false))

(seq-div-by? 17 [20 19 18])

(defn narrow-factored-range
  [a b]
  (let [r-min (min a b)
        r-max (max a b)]
    (loop [my-range [r-max]
           num (dec r-max)]
      (if (< num r-min)
        (reverse my-range)
        (let [new-range (if (seq-div-by? num my-range)
                          my-range
                          (conj my-range num))]
          (recur new-range (dec num)))))))

(narrow-factored-range 1 20)

(defn div-by-range?
  [num a b]
  (loop [div a]
    (if (not (div-by? num div))
      false
      (let [div (inc div)]
        (if (> div b)
          true
          (recur div))))))

(div-by-range? 2520 2 10)

(defn div-by-seq?
  [num my-seq]
  (loop [[div & my-seq] my-seq]
    (if div
      (if (div-by? num div)
        (recur my-seq)
        false)
      true)))

  ; (if (first my-seq)
  ;   (do
  ;     ; (println "dbs:" num "r:" my-seq)
  ;     (if (div-by? num (first my-seq))
  ;       (recur num (rest my-seq))
  ;       false))
  ;   true))

(div-by-seq? 2520 (range 2 11))

(defn small-mult
  [a b]
  ; (let [my-range (narrow-factored-range a b)]
  ;   my-range))
  (let [r-min (min a b)
        r-max (max a b)]
    (loop [num r-max]
      (if (div-by-range? num r-min r-max)
        num
        (recur (inc num))))))

(defn n-small-mult
  [a]
  (let [my-range (narrow-factored-range 1 a)]
    (print "my-range:" my-range)
    (loop [num (inc a)]
      ; (println "Checking:" num)
      (if (div-by-seq? num my-range)
        num
        (recur (inc num))))))

(defn small-mult-less
  [a]
  (let [my-range (narrow-factored-range 1 a)
        r-min (apply min my-range)
        r-max (apply max my-range)]
    (loop [num r-max]
      (if (div-by-range? num r-min r-max)
        num
        (recur (inc num))))))

(small-mult 2 20)
(small-mult 2 10)
(small-mult 1 20)
(n-small-mult 10)
(narrow-factored-range 1 8)
(seq-div-by? 12 '(6 7 8 9 10))
(div-by-seq? 2520 '(6 7 8 9 10))
(n-small-mult 8)
(small-mult 2 8)
(div-by-seq? 840 '(5 6 7 8))

(time (n-small-mult 11))
(time (small-mult 2 11))
(time (small-mult-less 11))

(narrow-factored-range 1 11)
(do
  (time (div-by-range? 27720 6 11))
  (time (div-by-seq? 27720 '(6 7 8 9 10 11)))
  (time (div-by-range? 27720 2 11)))
