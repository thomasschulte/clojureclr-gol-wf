(ns BoardTest)

(use '[Board :only (make-board next-board)])
(use '[clojure.test])

(deftest check-initial-board-test
  (let [b (make-board 5)]
    (is (= 5 (count b)))
    (is (= 5 (count (nth b 0))))
    (is (= 5 (count (nth b 1))))
    (is (= 5 (count (nth b 2))))
    (is (= 5 (count (nth b 3))))
    (is (= 5 (count (nth b 4))))
    ))

(deftest blinker-test
  (let [b [[:u :u :u] 
           [:s :s :s]
           [:u :u :u]]]
    (is (= [[:u :s :u] 
            [:u :s :u]
            [:u :s :u]] (next-board b)))
    (is (= b (next-board (next-board b))))  
    ))

(deftest quader-test
  (let [b [[:u :u :u :u] 
           [:u :s :s :u]
           [:u :s :s :u]
           [:u :u :u :u]]]
    (is (= b (next-board b)))
    (is (= b (next-board (next-board b))))
    ))

(deftest cross-test
  (let [b [[:u :u :u :u :u] 
           [:u :u :s :u :u]
           [:u :s :u :s :u]
           [:u :u :s :u :u]
           [:u :u :u :u :u]]]
    (is (= b (next-board b)))
    (is (= b (next-board (next-board b))))
    ))

(deftest bipol-test
  (let [b [[:s :s :u :u :u] 
           [:s :s :u :u :u]
           [:u :u :s :s :u]
           [:u :u :s :s :u]
           [:u :u :u :u :u]]]
    (is (= [[:s :s :u :u :u] 
           [:s :u :u :u :u]
           [:u :u :u :s :u]
           [:u :u :s :s :u]
           [:u :u :u :u :u]] (next-board b)))
    (is (= b (next-board (next-board b))))
    ))

;; Test runs
(check-initial-board-test)
(blinker-test)
(quader-test)
(cross-test)
(bipol-test)



