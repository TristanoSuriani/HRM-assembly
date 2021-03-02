; Tetracontiplier
; Takes all the elements from the inbox and sends their value multiplied by 40 to the outbox.

.setup
    push inbox 3
    push inbox 2
    push inbox -4
    push inbox 0

.program
    start:
        inbox
        copyto 0
        add 0
        copyto 1
        add 1
        copyto 2
        add 2
        copyto 3
        add 3
        add 3
        add 3
        add 3
        outbox
        jump start

.test
    contains outbox 120 80 -160 0
