(ns GameOfLife
  (:gen-class))

(use '[Board :only (make-board next-board)])

;; UI Handling for game of life
;; Windows forms technologie
(System.Reflection.Assembly/LoadWithPartialName "System.Windows.Forms")

(import (System.Drawing 
          Size Point Color SolidBrush))
(import (System.Windows.Forms
          Form TableLayoutPanel Label Button TextBox Panel Timer PaintEventHandler PaintEventArgs))

(defn- paint 
  "Paints the board to the panel"
  [pnl board]
  (let [length (count board)
        black (SolidBrush. Color/Black)
        white (SolidBrush. Color/White)
        w (int (/ (.get_Width pnl) length))
        h (int (/ (.get_Height pnl) length))
        g (.CreateGraphics pnl) ]
    (doseq [y (range length)
            x (range length)]
      (let [row (nth board y)
            cell (nth row x)]
      (if (= cell :s)
        (.FillRectangle g black (* x w) (* y h) w h)
        (.FillRectangle g white (* x w) (* y h) w h)
        ))
    )
    (.Dispose black)
    (.Dispose white)
    (.Dispose g)
    ))

(defn game-of-life 
  "Main program for starting the game with a square of length"
  [length]
   (let [form (Form.)
         pnl-paint (Panel.)
         btnNewGame (Button.)
         btnStartStop (Button.)
         txtCount (TextBox.)
         gameTimer (Timer.)
         gameCount (atom 1)
         board (atom (make-board length))]

     (doto btnNewGame
       (.set_Text "New Game")
       (.set_Size (Size. 100 25))
       (.set_Location (Point. 1 10)))

     (doto btnStartStop
       (.set_Tag "IsStoped")
       (.set_Text "Start Game")
       (.set_Size (Size. 100 25))
       (.set_Location (Point. 120 10)))

     (doto txtCount
       (.set_Size (Size. 100 25))
       (.set_Location (Point. 240 10)))

     (doto gameTimer
       (.set_Interval 500))

     (doto pnl-paint
       (.set_Size (Size. 287 219))
       (.set_Location (Point. 1 42))
       (.set_Anchor 15)
       (.set_BackColor Color/White))
         
     (.. form Controls (Add btnNewGame))
     (.. form Controls (Add btnStartStop))
     (.. form Controls (Add txtCount))
     (.. form Controls (Add pnl-paint))

     (.add_Tick gameTimer
       (gen-delegate EventHandler [sender args]
         (do
           (.set_Text txtCount (str @gameCount))
           (paint pnl-paint @board)
           (swap! gameCount inc)
           (reset! board (next-board @board))
           )))
     	 
    (.add_Click btnNewGame
      (gen-delegate EventHandler [sender args]
          (do
            (reset! board (make-board length))
            (reset! gameCount 1))))

     (.add_Click btnStartStop
       (gen-delegate EventHandler [sender args]
         (if (= (.get_Tag btnStartStop) "IsStoped")
           (do
             (.set_Tag btnStartStop "IsStarted")
             (.set_Text btnStartStop "Stop Game")
             (.Start gameTimer))
           (do
             (.set_Tag btnStartStop "IsStoped")
             (.set_Text btnStartStop "Start Game")
             (.Stop gameTimer)))))
	                                  
      (doto form             
        (.set_Text "Game of Life")
        (.set_Size (Size. 300 300))
        .ShowDialog)))

(defn -main 
  "Main Entry Point, starts the game with a board lengh of 50"
  [& args]
  (game-of-life 50))