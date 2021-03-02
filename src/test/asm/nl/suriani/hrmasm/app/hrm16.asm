; Absolute Positivity
; Takes each element from the inbox and sends its absolute value to the outbox.

.setup
    push inbox 6
    push inbox -4
    push inbox 3
    push inbox 0
    push inbox -5
    push inbox -2
    push inbox 5
    push inbox 7

.program
    start:
        inbox
        jumpn make-absolute
        outbox
        jump start

    make-absolute:
        copyto 0
        sub 0
        sub 0
        outbox
        jump start

.test
    contains outbox 6 4 3 0 5 2 5 7
