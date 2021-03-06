(ns fwpd.core)
(def filename "suspects.csv")

(def vamp-keys [:name :glitter-index])

(defn str->int
  [str]
  (Integer. str))

(def conversions {:name identity
                  :glitter-index str->int})

(defn convert
  [vamp-key value]
  ((get conversions vamp-key) value))

(defn parse
  "Convert a CSV into rows of columns"
  [string]
  (map #(clojure.string/split % #",")
       (clojure.string/split string #"\n")))

; (defn records-to-csv
;   [records])


(defn mapify
  "Return a seq of maps like {:name \"Edward Cullen\" :glitter-index 10}"
  [rows]
  (map (fn [unmapped-row]
         (reduce (fn [row-map [vamp-key value]]
                   (assoc row-map vamp-key (convert vamp-key value)))
                 {}
                 (map vector vamp-keys unmapped-row)))
       rows))

(defn glitter-filter
  [minimum-glitter records]
  (filter #(>= (:glitter-index %) minimum-glitter) records))

(defn glitter-filter-names
  [minimum-glitter records]
  (map :name (glitter-filter minimum-glitter records)))

(defn append
  [records suspect]
  (conj records suspect))

(defn validate
  [validate-funcs value]
  (loop [[keyword & keywords] (keys validate-funcs)]
    (if (nil? keyword)
      true
      (if ((keyword validate-funcs) (keyword value))
        (recur keywords)
        false))))

(defn record-to-csv
  [record]
  (clojure.string/join ","
    (list (:name record) (:glitter-index record))))

(defn suspects-to-csv
  [suspects]
  (let [suspect-csv-list
        (loop [[record & suspects] suspects
               suspect-csv-list '()]
          (if (nil? record)
            suspect-csv-list
            (recur suspects
                   (conj suspect-csv-list (record-to-csv record)))))]
       (format "%s\n" (clojure.string/join "\n" suspect-csv-list))))
