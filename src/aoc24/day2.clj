(ns aoc24.day2
  (:require [clojure.string :as str]))

(def input
  (->> "resources/day2"
       slurp
       str/split-lines))

(defn format [input]
  (map #(map parse-long (str/split % #" ")) input))

(defn in-range-differs [levels]
  (every? true?
          (map (fn [[x y]] (<= 1 (abs (- x y)) 3))
               (partition 2 1 levels))))

(defn safe? [levels]
  (and (or (apply < levels) (apply > levels))
       (in-range-differs levels)))

(defn safe-reports [input]
  (->> input
       format
       (filter safe?)
       count))


;;;; Part 2

(defn bad-level-by [f parts]
  (->> parts
       (filter (fn [[a b]] (not (f a b))))
       first
       second))

(defn bad-levels [levels]
  (let [parts (partition 2 1 levels)]
    ((juxt (partial bad-level-by >)
           (partial bad-level-by <)
           (partial bad-level-by (fn [x y] (<= 1 (abs (- x y)) 3))))
     parts)))

(defn tolerable-safe? [levels]
  (let [bad-levels (bad-levels levels)]
    (some (fn [bad-level] (safe? (remove #(= % bad-level) levels)))
          bad-levels)))

(defn tolerable-reports [input]
  (->> input
       format
       (filter #(or (safe? %) (tolerable-safe? %)))
       count))

(comment
  ;; Part 1
  (safe-reports input)

  ;; Part 2
  (tolerable-reports input)

  )
