; Zero Preservation Initiative
; Takes all the elements from the inbox and sends only the ones that are 0 to the outbox.

.setup
    push inbox 3
    push inbox 0
    push inbox 8
    push inbox C
    push inbox 0
    push inbox 0
    push inbox -4
    push inbox 0

.program
    start:
        inbox
        jump0 to-the-outbox
        copyto 0
        jump start

    to-the-outbox:
        outbox
        jump start

.test
    contains outbox 0 0 0 0
