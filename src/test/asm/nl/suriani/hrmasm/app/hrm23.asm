; The Littlest Number
; Takes each zero terminated string and sends the littlest number (that is not 0) to the outbox.

.setup
    push inbox 6
    push inbox 25
    push inbox 10
    push inbox 34
    push inbox 0
    push inbox 10
    push inbox 0
    push inbox 13
    push inbox 2
    push inbox 55
    push inbox 21
    push inbox 9
    push inbox 0
    push inbox 9
    push inbox -2
    push inbox 1
    push inbox 5
    push inbox 0

.program

    alias #smallest 0
    alias #current 1

    start:
        inbox
        copyto #smallest
        copyto #current

    next-elements:
        inbox
        jump0 to-the-outbox
        copyto #current
        copyfrom #smallest
        sub #current
        jumpn next-elements
        copyfrom #current
        copyto #smallest
        jump next-elements

    to-the-outbox:
        copyfrom #smallest
        outbox
        jump start

.test
    contains outbox 6 10 2 -2
