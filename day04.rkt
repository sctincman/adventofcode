#lang racket

(require openssl/md5)

(define salt "bgvyzdsv")

;Part 1
(let loop [(value 1)]
  (if (equal? "00000"
	      (substring (call-with-input-string
			     (string-append salt
					    (number->string value))
			   md5) 0 5))
      value
      (loop (+ 1 value))))

;Part 2
(let loop [(value 1)]
  (if (equal? "000000"
	      (substring (call-with-input-string
			     (string-append salt
					    (number->string value))
			   md5) 0 6))
      value
      (loop (+ 1 value))))