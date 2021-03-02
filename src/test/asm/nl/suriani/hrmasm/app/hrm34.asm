; Vowel incinerator
; For each letter in the inbox it sends the letters which are consonants to the outbox.
.setup

    push inbox I
    push inbox T
    push inbox F
    push inbox A
    push inbox C
    push inbox E
    push inbox F
    push inbox I
    push inbox S
    push inbox H

    set R0 A
    set R1 E
    set R2 I
    set R3 O
    set R4 U
    set R5 0

.program

    alias #zero 5
    alias #vowels 0
    alias #counter 6
    alias #current 7

    start:
        copyfrom #zero
        copyto #counter
        inbox
        copyto #current

    check-vowels-loop:
        copyfrom* #counter
        jump0 to-the-outbox
        sub #current
        jump0 start
        bump+ #counter
        jump check-vowels-loop

    to-the-outbox:
        copyfrom #current
        outbox
        jump start

.test
    contains outbox T F C F S H
