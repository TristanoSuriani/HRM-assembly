; Tripler Room
; Takes all the elements from the inbox and sends their value multiplied by 3 to the outbox.

.setup
    push inbox 3
    push inbox -2
    push inbox 6
    push inbox 0

.program
    start:
        inbox
        copyto 0
        add 0
        add 0
        outbox
        jump start

.test
    contains outbox 9 -6 18 0
