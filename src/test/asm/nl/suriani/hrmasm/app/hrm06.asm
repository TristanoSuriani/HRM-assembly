; Rainy summer
; Takes each pair of elements from the inbox and sends its sum to the outbox.

.setup
    push inbox 4
    push inbox 7
    push inbox 3
    push inbox 1
    push inbox -1
    push inbox 2
    push inbox 6
    push inbox -1

.program
    start:
        inbox
        copyto 0
        inbox
        add 0
        outbox
        jump start

.test
    contains outbox 11 4 1 5
