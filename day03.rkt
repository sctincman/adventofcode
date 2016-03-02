#lang racket

(define (pair-sum pair1 pair2)
  (list (+ (first pair1) (first pair2)) (+ (second pair1) (second pair2))))

(define (parse-direction char)
  (if (char? char)
      (cond
       [(equal? char #\^) '(0  1)]
       [(equal? char #\v) '(0 -1)]
       [(equal? char #\>) '(1  0)]
       [(equal? char #\<) '(-1 0)])
      '(0 0)))

; Part 1
(set-count (list->set (call-with-input-file "day03.txt"
			(lambda (in)
			  (foldl (lambda (char sum)
				   (let [(current-pos (car sum))]
				     (cons (pair-sum current-pos (parse-direction char)) sum)))
				 '((0 0))
				 (port->list read-char in))))))

; Part 2
(define (read-pair in)
  (let [(first-char (read-char in))]
    (if (eof-object? first-char)
	first-char
	(list first-char (read-char in)))))

(set-count (list->set (call-with-input-file "day03.txt"
			(lambda (in)
			  (foldl (lambda (chars sum)
				   (let [(santa-pos (first sum))
					 (robo-pos (second sum))]
				     (cons (pair-sum santa-pos (parse-direction (first chars)))
					   (cons (pair-sum robo-pos (parse-direction (second chars)))
						 sum))))
				 '((0 0) (0 0))
				 (port->list read-pair in))))))
