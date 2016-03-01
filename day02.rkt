#lang racket

; Part 1
(define (parse-triple string)
  (map string->number (string-split string "x")))

(define (find-area triple)
  (let [(areas (list (* (first triple) (second triple))
		     (* (first triple) (third triple))
		     (* (second triple) (third triple))))]
    (+ (apply min areas) (* 2 (apply + areas)))))

(call-with-input-file "day02.txt"
  (lambda (in)
    (foldl (lambda (line sum)
	     (+ sum (find-area (parse-triple line))))
	   0
	   (port->list read-line in))))

; Part 2
(define (find-length triple)
  (+ (apply * triple)
     (* 2 (- (apply + triple) (apply max triple)))))

(call-with-input-file "day02.txt"
  (lambda (in)
    (foldl (lambda (line sum)
	     (+ sum (find-length (parse-triple line))))
	   0
	   (port->list read-line in))))
