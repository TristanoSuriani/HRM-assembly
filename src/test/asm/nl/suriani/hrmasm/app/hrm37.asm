; Scavenger Chain
; Each pair on the floor contains:
;  1. data
;  2. the address of another one of the pairs
; Outbox the data for that pair, and also the data in all following pairs in the chain.
; Each thing in the inbox is an address to one of the pairs. The chain ends when you reach a negative address.

.setup
    push inbox 0
    push inbox 23

    set R0 E
    set R1 13

    set R3 C
    set R4 23

    set R10 P
    set R11 20

    set R13 S
    set R14 3

    set R20 E
    set R21 -1

    set R23 A
    set R24 10

.program
    alias #next 2

    start:
        inbox
        copyto #next

    loop:
        copyfrom #next
        jumpn start
        copyfrom* #next
        outbox
        bump+ #next
        copyfrom* #next
        copyto #next
        jump loop


.test
    contains outbox E S C A P E A P E
