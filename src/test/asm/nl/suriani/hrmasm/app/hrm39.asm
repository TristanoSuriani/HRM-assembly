; Re-Coordinator
; Each tile on the floor has a coordinate x,y (for example the tile 6 has a coordinate 2, 1. (column first, row second)
; In the inbox there are the addresses whose coordinates have to be sent to the outbox.

.setup
    push inbox 15
    push inbox 2
    push inbox 4
    push inbox 6

    set R14 0
    set R15 4

.program
    alias #current 0
    alias #column 1
    alias #mod 1
    alias #row 2
    alias #quotient 2
    alias #zero 14
    alias #four 15

    start:
        inbox
        copyto #current
        copyfrom #zero
        copyto #quotient

    division-loop:
        copyfrom #current
        sub #four
        jumpn to-the-outbox
        copyto #current
        bump+ #quotient
        jump division-loop

    to-the-outbox:
        copyfrom #current
        copyto #mod
        copyfrom #column
        outbox
        copyfrom #row
        outbox
        jump start

.test
    contains outbox 3 3 2 0 0 1 2 1
