; Cumulative countdown
; Takes each number from the inbox and send the sum of the countdown down to 0 to the outbox.

.setup
    push inbox 4
    push inbox 5
    push inbox 3
    push inbox 0
    push inbox 10

    set R4 0

.program

    alias #n 0
    alias #accumulator 1
    alias #counter 2
    alias #zero 4

    start:
        copyfrom #zero
        copyto #accumulator
        inbox
        copyto #n
        jump0 to-the-outbox
        copyto #counter

    sum-loop:
        copyfrom #counter
        jump0 to-the-outbox
        add #accumulator
        copyto #accumulator
        bump- #counter
        jump sum-loop

    to-the-outbox:
        copyfrom #accumulator
        outbox
        jump start

.test
    contains outbox 10 15 6 0 55
