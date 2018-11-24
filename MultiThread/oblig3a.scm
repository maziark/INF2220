;; oblig 3a

(load "prekode3a.scm")


;; Oppgave 1
;; a)

;; Funksjonen fungerer kun hvis alle elementene er i rekkefoelge, feks (2, 3, 4)
(define (list-to-stream elements)
  (if (stream-null? elements)
      '()
      (cons-stream (car elements)
                   (list-to-stream (cdr elements)))))


(define (stream-to-list stream . n)
  
  (define (iter stream counter)
    (if (or (= (car n) counter)
            (stream-null? stream))
        '()
        (cons (stream-car stream)
              (iter (stream-cdr stream) (+ counter 1)))))
  
  (if (null? n)
      (if (stream-null? stream)
          '()
          (cons (stream-car stream)
                (stream-to-list (stream-cdr stream))))
      (iter stream 0)))

;; Test under
#|
(define lol (list-to-stream '(9 1 7 8)))
lol
(stream-to-list lol)
(stream-to-list lol 2)
|#

;; b)

(define (stream-map proc . argstreams)
  
  (define (is-finished? argstreams)
    (if (null? argstreams)
        #f
        (if (null? (car argstreams))
            #t
            (is-finished? (cdr argstreams)))))
  
  (if (is-finished? argstreams)
      the-empty-stream
      (cons-stream
       (apply proc (map stream-car argstreams))
       (apply stream-map
              (cons proc (map stream-cdr argstreams))))))

;; Test under
#|
(define hello (stream-interval 10 20))
(define halla (stream-interval 2 10))
hello
halla

(define yo (stream-map + hello halla))
(stream-to-list yo)
|#


;; c)

;; Petter Smart har ikke tenkt på at listen kan være uendelig. Hvis kan bruker remove-duplicates på en uendelig strøm vil prosedyren fortsette i en evig loop


;; d)

#|
I REPLet blir følgende skrevet ut:
0
1
2
3
4
5
5
6
7
7
Dette er fordi at når man oppretter x er kun det første elementet evaluert i show, derfor vil
kun 0 vises om man ikke kaller på stream-ref eller en liknende prosedyre. Derimot når man kaller
på (stream-ref x 5) så evalueres alle elementene i streamen opp til 5 og displayer 1 2 3 4 5, for
så at stream-ref returnerer 5 som resulterer i 0 1 2 3 4 5 5. Det samme gjelder for 7 men alt fra 0
til 5 er allerede evaluert av show. Delayet skjer i stream-map fordi at stream-map er lazy og evaluerer
ikke noe før den blir forced.

|#

;; Oppgave 2
;; a)

(define (make-lm)
  (list (list '*lm*)))

#|(define (lm-lookup-bigram lm string1 . string2)
  (if (assoc (list string1 string2) lm)
      (cadr (assoc (list string1 string2) lm))
      (cadr (assoc (list string1) lm))))
|#
(define (lm-lookup-bigram lm string1 . string2)
  (if (eq? string2 '())
      (if (assoc (list string1) lm)
          (cadr (assoc (list string1) lm))
          #f)
      (if (assoc (list string1 (car string2)) lm)
          (car (cdr (assoc (list string1 (car string2)) lm)))
          #f)))



(define (lm-record-bigram! lm string1 string2)
  (define (helper-record-oneword!)
    (if (assoc (list string1) lm)
        ;;øk frekvens av ord
        (set-car! (cdr (assoc(list string1) lm))
                  (+ (cadr (assoc (list string1) lm)) 1))
        ;;legg til nytt ord)
        (let ((newElement (list (list string1) 1)))
          (set-cdr! lm (cons newElement (cdr lm))))
        ))
  (if (lm-lookup-bigram lm string1 string2)
      (set-car! (cdr (assoc (list string1 string2) lm))
                (+ (cadr (assoc (list string1 string2) lm)) 1))
      (let ((newElement (list (list string1 string2) 1)))
        (set-cdr! lm (cons newElement (cdr lm)))))
  
  (helper-record-oneword!))

;; Test under
#|
(define bigram (make-lm))
(lm-record-bigram! bigram "a" "b")
(lm-record-bigram! bigram "c" "d")
(lm-record-bigram! bigram "a" "b")
bigram
(lm-lookup-bigram bigram "a" "b")
|#


;; b)

(define brown (read-corpus "brown.txt"))

(define (lm-train! corpus lm)
  
  (define (inner! sent)
    (if (not (eq? (car sent) "</s>"))
        (begin (lm-record-bigram! lm (car sent) (cadr sent))
               (inner! (cdr sent)))))
  
  (define (iter! elements)
    (if (not (null? elements))
        (begin (inner! (car elements))
               (iter! (cdr elements)))))
  (iter! corpus))


;;Tester under

(define bigram-brown (make-lm))
(lm-train! brown bigram-brown)
(lm-lookup-bigram bigram-brown "jury" "said")



;; c)
;;Dette virker ikke helt som vi tenkte det skulle og det er sikkert pga en en eller annen idiotfeil.
;;om du ser hvorfor dette ikke funker hadde det vært fint med en tilbakemelding om hvordan det eventuelt kunne blitt fikset
;;veldig kul oblig, gøy.
#|
(define (lm-estimate lm)
  (define (helper-adder! pair)
    (list pair (/ (cadr (assoc pair lm)) (cadr (assoc (list (car pair)) lm)))))
  (define (helper-estimate! intm lst)
    (if (not (null? intm))
       (if (= (length (caar intm)) 2)
           (let ((newElement (helper-adder! (caar intm))))
             (helper-estimate! (cdr intm) (set-cdr! lst (cons newElement (cdr lst)))))
           (helper-estimate! (cdr intm) lst))
       lst))
    (helper-estimate! (cdr lm) (make-lm)))

(define probs (lm-estimate bigram-brown))
|#