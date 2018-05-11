(defn sq-sums
  [x]
  (let [sum (apply + (range 1 (inc x)))]
    (* sum sum)))

(sq-sums 10)

(defn sum-squares
  [x]
  (reduce
    (fn [y])))

(defn sq-seq
  ([] (sq-seq 1))
  ([n] (lazy-seq
         (cons (* n n) (sq-seq (inc n))))))

(defn square-sums
  [n]
  (apply + (take n (sq-seq))))

(defn sq-sum-diff
  [n]
  (-
    (sq-sums n)
    (square-sums n)))

(sq-sum-diff 100)
