; Storage Floor
; Takes each element from the inbox and sends the value at the tile with the same number to the outbox.
.setup

    push inbox 10
    push inbox 9
    push inbox 8
    push inbox 7
    push inbox 6

    set R0 10
    set R1 9
    set R2 8
    set R3 7
    set R4 6

    set R6 E
    set R7 V
    set R8 A
    set R9 L
    set R10 S

.program

    alias #input 12

    start:
        inbox
        copyto #input
        copyfrom* #input
        outbox
        jump start

.test
    contains outbox S L A V E
