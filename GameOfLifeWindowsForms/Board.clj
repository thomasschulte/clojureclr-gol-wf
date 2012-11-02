(ns Board)

;; board handling
;; independent of ui
(defn- make-row 
  "Makes a sequence of length which is randomly filled with :s or :u"
  [length]
  (for [x (range length)]
    (if (< (rand 10) 5)
      :s
      :u)))

(defn make-board 
  "Makes a board as square of length with :s :u randomly set"
  [length]
  (for [x (range length)]
    (make-row length)))

(defn- neighbour-count 
  "Calculates the count of neighbours with :s of x/y in board"
  [board x y]
  (let  [length (count board)
        r1 (nth board (- y 1) (repeat length :u))
        r2 (nth board y)
        r3  (nth board (+ y 1) (repeat length :u))
        r11 [(nth r1 (- x 1) :u) (nth r1 x) (nth r1 (+ x 1) :u)]
        r21 [(nth r2 (- x 1) :u) :u         (nth r2 (+ x 1) :u)]
        r31 [(nth r3 (- x 1) :u) (nth r3 x) (nth r3 (+ x 1) :u)]
        ]
    (count (filter #(= % :s)(concat r11 r21 r31)))))

(defn next-board 
  "Calculates the next board regarding conway law's"
  [board]
  (let [length (count board)]
    (for [y (range length)]
      (let [row (nth board y)]
        (for [x (range length)]
          (let [count (neighbour-count board x y)]
            (if (= (nth row x) :s)
              (if (or (= count 2) (= count 3))
                :s
                :u)
              (if (= count 3)
                :s
                :u))))))))