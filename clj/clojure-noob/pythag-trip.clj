(defn square
  [x] (* x x))
  ; (let [sq (* x x)]
  ;   (println "sq x:" x "=" sq)
  ;   sq))

(square 3)

(defn pythag-trip?
  [a b c]
  (= (square c) (+ (square a) (square b))))

(pythag-trip? 3 4 5)

(for [x (range 3) y (range 3)] [x y])

(defn pythag-candidates
  [c-max]
  (for [c (range 3 (inc c-max)) b (range 2 c-max) a (range 1 (dec c-max))
        :while (and (< a b) (< b c))]
      [a b c]))

(defn l-pythag-candidates
  ([] (l-pythag-candidates 1 2 3))
  ([a b c]
   (lazy-seq
     (if (= a b)
       (l-pythag-candidates 1 (inc b) c)
       (if (= b c)
         (l-pythag-candidates 1 2 (inc c))
         (cons [a b c] (l-pythag-candidates (inc a) b c)))))))

(pythag-candidates 5)
(take 10 (l-pythag-candidates))

(let [s (l-pythag-candidates)
      a (first s)
      b (rest s)]
  [a (first b)])

; (defn gen-pyth-c
;   ([] (gen-pyth-c 1 2 3))
;   ([a b c]
;    (lazy-seq
;      (if (= a b)
;        (gen-pyth-c a (inc b) c)
;        (if (= b c)
;          (gen-pyth-c a b (inc c))
;          (if (pythag-trip? a b c)
;            (cons [a b c] (gen-pyth-c (inc a) b c))
;            (gen-pyth-c (inc a) b c)))))))

(defn l-gen-pyth-c
  ([] (l-gen-pyth-c (l-pythag-candidates)))
  ([candidates]
   (lazy-seq
     (let [[a b c] (first candidates)]
       (if (pythag-trip? a b c)
         (cons [a b c] (l-gen-pyth-c (rest candidates)))
         (l-gen-pyth-c (rest candidates)))))))

(take 5 (gen-pyth-c))
(take 5 (l-gen-pyth-c))
(loop [[[a b c] & candidates] (l-gen-pyth-c)]
  (if (= 1000 (+ a b c))
    [a b c]
    (recur candidates)))

(apply * (loop [[[a b c] & candidates] (l-gen-pyth-c)]
           (if (= 1000 (+ a b c))
             [a b c]
             (recur candidates))))
