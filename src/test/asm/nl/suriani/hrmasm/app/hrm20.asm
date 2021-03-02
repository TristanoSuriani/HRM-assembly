; Multiplication Workshop
; Takes each pairs of elements and sends their multiplication to the outbox.

.setup
    push inbox 9
    push inbox 4

    push inbox 6
    push inbox 9

    push inbox 7
    push inbox 0

    push inbox 0
    push inbox 6

    push inbox 4
    push inbox 5

    set R7 0

.program

    ; Aliases! It sorts of look like the comment on the tiles in the game.
    alias #first 0
    alias #second 1
    alias #counter 2
    alias #accumulator 3
    alias #zero 7

    start:
        ; --------------------------------------------------
        inbox
        copyto #first

        inbox
        copyto #second

        jump0 its-0
        copyfrom #first
        jump0 its-0

        copyfrom #second
        copyto #counter
        copyfrom #first
        copyto #accumulator

    multiplication-loop:
        bump- #counter
        jump0 send-to-outbox

        copyfrom #first
        add #accumulator
        copyto #accumulator

        jump multiplication-loop

    send-to-outbox:
        copyfrom #accumulator
        outbox
        jump start

    its-0:
        copyfrom #zero
        outbox
        jump start

.test
    contains outbox 36 54 0 0 20
