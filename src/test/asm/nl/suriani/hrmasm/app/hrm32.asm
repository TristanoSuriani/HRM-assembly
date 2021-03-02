; Inventory report
; For each letter in the inbox it sends the total amount of the matching letters on the floor.
.setup

    push inbox X
    push inbox B
    push inbox A
    push inbox C

    set R0 A
    set R1 C
    set R2 B
    set R3 X
    set R4 X
    set R5 X
    set R6 A
    set R7 A
    set R8 C
    set R9 B
    set R10 A
    set R11 B
    set R12 B
    set R13 C
    set R14 0

.program

    alias #zero 14
    alias #counter 15
    alias #current 16
    alias #matches 17

    start:
        copyfrom #zero
        copyto #counter
        copyto #matches
        inbox
        copyto #current

    match-loop:
        copyfrom* #counter
        jump0 to-the-outbox
        sub #current
        jump0 add-match
        bump+ #counter
        jump match-loop

    add-match:
        bump+ #counter
        bump+ #matches
        jump match-loop

    to-the-outbox:
        copyfrom #matches
        outbox
        jump start

.test
    contains outbox 3 4 4 3
