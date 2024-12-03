(ns aoc24.day3)

(def input
  (->> "resources/day3"
       slurp))

(defn add-muls [input]
  (->> input
       (re-seq #"mul\((\d+),(\d+)\)")
       (map #(apply * (map parse-long (rest %))))
       (reduce +)))

(defn add-enabled-muls [input]
  (let [instructions (re-seq #"mul\((\d+),(\d+)\)|do\(\)|don't\(\)" input)]
    (reduce (fn [[enabled? acc] instruction]
              (let [[group & xs] instruction]
                (case group
                  "don't()" [false acc]
                  "do()" [true acc]
                  [enabled? (if enabled?
                              (+ (apply * (map parse-long xs)) acc)
                              acc)])))
            [true 0]
            instructions)))

(comment
  ;; Part 1
  (add-muls input)

  ;; Part 2
  (add-enabled-muls input)
  )
