; String Storage Floor
; Takes each element from the inbox and sends the value at the tile with the same number
;   plus all the values on the next tiles to the outbox until reaching a 0.
.setup

    push inbox 4
    push inbox 15
    push inbox 7
    push inbox 0
    push inbox 21
    push inbox 17
    push inbox 11
    push inbox 21
    push inbox 2
    push inbox 13
    push inbox 4
    push inbox 17
    push inbox 22

    set R0 G
    set R1 E
    set R2 T
    set R3 0
    set R4 T
    set R5 H
    set R6 0
    set R7 T
    set R8 A
    set R9 R
    set R10 0
    set R11 A
    set R12 W
    set R13 A
    set R14 K
    set R15 E
    set R16 0
    set R17 I
    set R18 S
    set R19 0
    set R20 X
    set R21 X
    set R22 X
    set R23 0

.program

    alias #next 24

    start:
        inbox
        copyto #next

    loop:
        copyfrom* #next
        jump0 start
        outbox
        bump+ #next
        jump loop

.test
    contains outbox T H E T A R G E T X X I S A W A K E X X T A K E T H I S X
