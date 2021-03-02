; Three Sort
; Takes each triplet of numbers from the inbox and send the smallest of the three to the outbox.
.setup
    push inbox 25
    push inbox 6
    push inbox 10

    push inbox 34
    push inbox 0
    push inbox 5

    push inbox 6
    push inbox 3
    push inbox 12

    push inbox 5
    push inbox 3
    push inbox 1


.program

    alias #first 0
    alias #second 1
    alias #third 2
    alias #result 3

    start:
        inbox
        copyto #first
        inbox
        copyto #second
        inbox
        copyto #third

    compare-first-and-second:
        copyfrom #first
        sub #second
        jumpn compare-first-and-third
        jump compare-second-and-third

    compare-first-and-third:
        copyfrom #first
        sub #third
        jumpn first-to-the-outbox
        jump third-to-the-outbox

    compare-second-and-third:
        copyfrom #second
        sub #third
        jumpn second-to-the-outbox
        jump third-to-the-outbox

    first-to-the-outbox:
        copyfrom #first
        outbox
        jump start

    second-to-the-outbox:
        copyfrom #second
        outbox
        jump start

    third-to-the-outbox:
        copyfrom #third
        outbox
        jump start

.test
    contains outbox 6 0 3 1
