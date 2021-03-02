; Fibonacci Visitor
; Takes each element of the inbox and sends the entire fibonacci sequence up to that element.

.setup
    push inbox 6
    push inbox 25
    push inbox 10
    push inbox 34

    set R4 0

.program

    alias #n-2 0
    alias #n-1 1
    alias #n 2
    alias #input 3
    alias #zero 4

    start:
        copyfrom #zero
        copyto #n-2
        copyto #n-1
        bump+ #n-1

    take-from-inbox:
        inbox
        copyto #input

    to-the-outbox:
        copyfrom #n-2
        add #n-1
        copyto #n
        copyfrom #input
        sub #n
        jumpn start
        copyfrom #n
        outbox

    prepare-next-iteration:
        copyfrom #n-1
        copyto #n-2
        copyfrom #n
        copyto #n-1
        jump to-the-outbox

.test
    contains outbox 1 2 3 5 1 2 3 5 8 13 21 1 2 3 5 8 1 2 3 5 8 13 21 34
