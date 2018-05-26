(glitter-filter-names 3 (mapify (parse (slurp filename))))

(parse (slurp filename))

(mapify (parse (slurp filename)))

(append (mapify (parse (slurp filename))) (first (mapify [["Bob Hoskins" "0"]])))

(mapify [["a" "1"]])

(def records (mapify (parse (slurp filename))))
records

(validate
  {:name string? :glitter-index integer?}
  {:name "Ed", :glitter-index "10"})
(validate
  {:name string? :glitter-index integer?}
  {:name "Ed", :glitter-indexes 10})
(validate
  {:name string? :glitter-index integer?}
  {:name 90210, :glitter-index 10})
(validate
  {:name string? :glitter-index integer?}
  {:names "Ed", :glitter-index 10})
(validate
  {:name string? :glitter-index integer?}
  {:name "Ed", :glitter-index 10})
(validate
  {:name string? :glitter-index integer?}
  {:name "Ed"})

(let [keyword :name] (keyword {:name "Edward Cullen", :glitter-index 10}))

(clojure.string/join
  "\n"
  (map #(clojure.string/join "," '((:name % (:glitter-index %))))))

(clojure.string/join "," '("Ed" 0))

(clojure.string/join ","
  (let [record {:name "Ed" :glitter-index 3}]
    (list (:name record) (:glitter-index record))))

(record-to-csv {:name "Bob" :glitter-index -2})

(clojure.string/join
  "\n"
  (map #(list (:name %) (:glitter-index %)) '({:name "Ed" :glitter-index 0}
                                              {:name "Lou" :glitter-index 2})))

(suspects-to-csv
  [{:name "Ed" :glitter-index 10}
   {:name "Bob" :glitter-index 0}])

(slurp filename)

(let [my-list (mapify (parse (slurp filename)))
      bob (first (mapify [["Bob Hoskins" "0"]]))
      augmented (append my-list bob)]
  (suspects-to-csv augmented))
