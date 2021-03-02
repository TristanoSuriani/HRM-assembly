; Copy floor
; Sends the elements in the registers 4, 0 and 3 and sends them to the outbox.

.setup
    push inbox -99
    push inbox -99
    push inbox -99
    push inbox -99
    set R0 U
    set R1 J
    set R2 X
    set R3 G
    set R4 B
    set R5 E

.program
    copyfrom 4
    outbox
    copyfrom 0
    outbox
    copyfrom 3
    outbox

.test
    contains outbox B U G
