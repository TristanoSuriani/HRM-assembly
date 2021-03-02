; Sub Hallway
; Takes each pair of elements from the inbox and sends the difference between the first and the second value to the outbox.

.setup
    push inbox 2
    push inbox 5
    push inbox 7
    push inbox 4
    push inbox -8
    push inbox -8
    push inbox 7
    push inbox -9

.program
    start:
        inbox
        copyto 0
        inbox
        copyto 1
        sub 0
        outbox
        copyfrom 0
        copyto 2
        sub 1
        outbox
        jump start

.test
    contains outbox 3 -3 -3 3 0 0 -16 16
