(ns aoc24.day1
  (:require [clojure.string :as str]))

(def input
  (->> "resources/day1"
       slurp
       str/split-lines))

(defn format [input]
  (->> input
       (map #(map parse-long (str/split % #"\s+")))
       (apply map vector)
       (map sort)))

(defn total-distance [input]
  (let [[left right] (format input)]
    (->> (map (comp abs -) left right)
         (reduce +))))

(defn similarity-score [input]
  (let [[left right] (format input)
        occurs (frequencies right)]
    (->> left
         (map #(* % (get occurs % 0)))
         (reduce +))))

(comment
  ;; Part 1
  (total-distance input)

  ;; Part 2
  (similarity-score input)

  )
