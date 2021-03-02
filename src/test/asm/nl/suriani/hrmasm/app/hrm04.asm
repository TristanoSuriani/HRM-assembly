; Scrambler handler
; Takes each pair of elements from the inbox and sends them to the outbox in reverse order.

.setup
    push inbox 4
    push inbox 9
    push inbox F
    push inbox A
    push inbox 2
    push inbox 7

.program
    start:
        inbox
        copyto 0
        inbox
        outbox
        copyfrom 0
        outbox
        jump start

.test
    contains outbox 9 4 A F 7 2
