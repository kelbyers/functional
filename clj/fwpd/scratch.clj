(glitter-filter-names 3 (mapify (parse (slurp filename))))

(parse (slurp filename))

(mapify (parse (slurp filename)))

(append (mapify (parse (slurp filename))) "Bob Hoskins" "0")

(mapify [["a" "1"]])

(def records (mapify (parse (slurp filename))))
records

(validate
  {:name string? :glitter-index integer?}
  {:name "Ed", :glitter-index 10})

(let [keyword :name] (keyword {:name "Edward Cullen", :glitter-index 10}))
