; Zero Exterminator
; Takes all the elements from the inbox and sends to the outbox only the ones that are not 0.

.setup
    push inbox 9
    push inbox 0
    push inbox 6
    push inbox B
    push inbox 0
    push inbox 0
    push inbox -4
    push inbox 0

.program
    start:
        inbox
        jump0 start
        outbox
        jump start

.test
    contains outbox 9 6 B -4
