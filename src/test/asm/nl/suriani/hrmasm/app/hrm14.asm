; Maximization Room
; Takes each pair from numbers from the inbox and sends the bigger of the two to the outbox (if equal, one of them).

.setup
    push inbox 1
    push inbox 4
    push inbox -6
    push inbox -1
    push inbox 8
    push inbox 8
    push inbox 1
    push inbox 7

.program
    start:
        inbox
        copyto 0
        inbox
        copyto 1
        sub 0
        jumpn send-first-to-outbox

    send-second-to-outbox:
        copyfrom 1
        outbox
        jump start

    send-first-to-outbox:
        copyfrom 0
        outbox
        jump start

.test
    contains outbox 4 -1 8 7
