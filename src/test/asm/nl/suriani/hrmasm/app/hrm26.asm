; Small Divide
; Takes each pair of numbers from the inbox and send the result of the division of the first by the second to the outbox.

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

    set R4 0

.program

    alias #first 0
    alias #second 1
    alias #accumulator 2
    alias #counter 3
    alias #zero 4

    start:
        inbox
        copyto #first
        copyto #accumulator
        inbox
        copyto #second
        copyfrom #zero
        copyto #counter

    division-loop:
        copyfrom #accumulator
        jump0 to-the-outbox
        sub #second
        jumpn to-the-outbox

        copyto #accumulator
        bump+ #counter
        jump division-loop

    to-the-outbox:
        copyfrom #counter
        outbox
        jump start

.test
    contains outbox 4 0 0 2 2 3
