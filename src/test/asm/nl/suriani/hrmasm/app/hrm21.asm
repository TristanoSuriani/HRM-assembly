; Zero Terminated Sum
; Takes each zero terminated string and send their sum to the outbox.

.setup
    push inbox 6
    push inbox 5
    push inbox 0

    push inbox 2
    push inbox -7
    push inbox 3
    push inbox 0

    push inbox 0
    push inbox 0
    push inbox 0
    push inbox 0

    push inbox 7
    push inbox -4
    push inbox 5
    push inbox 9
    push inbox -1
    push inbox 1
    push inbox 0

    set R4 0

.program

    alias #accumulator 0
    alias #zero 4

    start:
        copyfrom #zero
        copyto #accumulator

    add-loop:
        inbox
        jump0 send-to-outbox
        add #accumulator
        copyto #accumulator
        jump add-loop

    send-to-outbox:
        copyfrom #accumulator
        outbox
        jump start

.test
    contains outbox 11 -2 0 0 0 0 17
