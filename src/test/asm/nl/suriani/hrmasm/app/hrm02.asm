; Busy Mail Room
; Sends all the element of the inbox to the outbox.

.setup
    push inbox A
    push inbox U
    push inbox T
    push inbox O
    push inbox E
    push inbox X
    push inbox E
    push inbox C

.program
    start:
        inbox
        outbox
        jump start

.test
    contains outbox A U T O E X E C
