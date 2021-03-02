  alias #first 0
    alias #second 1
    alias #accumulator 2

    start:
        inbox
        copyto #first
        copyto #accumulator
        inbox
        copyto #second

    division-loop:
        copyfrom #accumulator
        jump0 to-the-outbox
        sub #second
        jumpn to-the-outbox

        copyto #accumulator
        jump division-loop

    to-the-outbox:
        copyfrom #accumulator
        outbox
        jump start

