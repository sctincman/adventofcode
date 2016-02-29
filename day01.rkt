#lang racket

; Part 1
(call-with-input-file "day01.txt"
  (lambda (in)
    (foldl (lambda (char sum)
	     (cond
	      [(equal? char #\( )
	       (+ sum 1)]
	      [(equal? char #\) )
	       (- sum 1)]))
	   0
	   (string->list (read-line in)))))

; Part 2
(call-with-input-file "day01.txt"
  (lambda (in)
    (let find-basement [(directions (string->list (read-line in)))
			(pos 0)
			(sum 0)]
      (if (or (< sum 0) (equal? directions '()))
	  pos
	  (cond
	   [(equal? (car directions) #\( )
	    (find-basement (cdr directions) (+ pos 1) (+ sum 1))]
	   [(equal? (car directions) #\) )
	    (find-basement (cdr directions) (+ pos 1) (- sum 1))])))))
      
