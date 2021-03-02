; Equalization Room
; Takes each pair of equal numbers from the inbox and sends one of them to the outbox if they are equal.

.setup
    push inbox 1
    push inbox 5
    push inbox 9
    push inbox 9
    push inbox 9
    push inbox 2

.program
    start:
        inbox
        copyto 0
        inbox
        sub 0
        jump0 to-the-outbox
        jump start

    to-the-outbox:
        copyfrom 0
        outbox
        jump start

.test
    contains outbox 9
