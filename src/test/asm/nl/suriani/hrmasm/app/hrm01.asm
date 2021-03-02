; Mail room
; Takes 3 elements from the inbox and sends them to the outbox.

.setup
    push inbox 1
    push inbox 2
    push inbox 3

.program
    inbox
    outbox
    inbox
    outbox
    inbox
    outbox

.test
    contains outbox 1 2 3
