; Sends the first 10 numbers of the Fibonacci sequence to the outbox.

.setup
    set R0 10 ; counter
    set R1 0 ; previous
    set R2 1 ; current
    set R10 1 ; step

.program
    start:
        ; calculates the sum and stores it in a temp register
        copyfrom 1
        add 2
        copyto 3

        ; sends the sum to the outbox
        outbox

        ; prepares for next iteration
        ; updates previous and current
        copyfrom 2
        copyto 1
        copyfrom 3
        copyto 2

        ; updates counter
        copyfrom 0
        sub 10
        copyto 0

        ; determines whether it must iterate or stop the execution
        jump0 end
        jump start
    end:

.test
    contains outbox 1 2 3 5 8 13 21 34 55 89
