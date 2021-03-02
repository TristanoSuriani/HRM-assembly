; Mod Module
; Takes each pair of numbers from the inbox and send the remainder of the division of the first by the second to the outbox.

.setup
    push inbox 25
    push inbox 6

    push inbox 10
    push inbox 34

    push inbox 0
    push inbox 5

    push inbox 6
    push inbox 3

    push inbox 12
    push inbox 5

    push inbox 3
    push inbox 1

.program

    alias #first 0
    alias #second 1
    alias #accumulator 2

    start:
        inbox
        copyto #first
        copyto #accumulator
        inbox
        copyto #second

    division-loop:
        copyfrom #accumulator
        jump0 to-the-outbox
        sub #second
        jumpn to-the-outbox

        copyto #accumulator
        jump division-loop

    to-the-outbox:
        copyfrom #accumulator
        outbox
        jump start

.test
    contains outbox 1 10 0 0 2 0
