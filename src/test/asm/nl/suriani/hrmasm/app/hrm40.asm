; Prime Factory
; For each thing in the inbox send its prime factors in order from smallest to biggest.

.setup
    push inbox 20
    push inbox 15
    push inbox 6
    push inbox 19
    push inbox 26

    set R24 0

.program
    ; A prime is a number that can only be divided for 1 or itself.
    ; We start finding a prime number < the number need to decompose. If we find the number itself we send it to the outbox.

    ; Then we divide the number by the prime. If the rest is 0 we update the current number with the result.
    ; Otherwise we try finding other numbers. If we find the original number we stop.

    ; We do it again.
    alias #one 16
    alias #counter 17
    alias #prime-candidate 18
    alias #prime 18
    alias #two 19
    alias #three 20
    alias #partial 21
    alias #current 22
    alias #original 23
    alias #zero 24

    prepare:
        copyfrom #zero
        copyto #counter
        copyto #two
        bump+ #two
        copyto #one
        bump+ #two
        copyto #three
        bump+ #three
        jump start

    start:
        inbox
        copyto #original
        copyto #current
        copyto #partial

        copyfrom #zero

        copyfrom #two
        copyto #prime-candidate      ;   The first prime candidate is 2.
        jump is-prime

    is-prime:
        ; if (current - 1 == 0) nothing more to do
        copyfrom #current
        sub #one
        jump0 start

        ; if (prime-candidate - current == 0) send current to outbox
        copyfrom #prime-candidate
        sub #current
        jump0 outbox-current

        ; if (prime-candidate - original == 0) send original to the outbox
        copyfrom #prime-candidate
        sub #original
        jump0 outbox-original


        ; if (prime-candidate == 2) do the division
        copyfrom #prime-candidate
        sub #two
        jump0 division-loop

        ; if (prime-candidate == 3) do the division
        copyfrom #prime-candidate
        sub #three
        jump0 division-loop

        ; else try with another prime candidate
        jump try-other-prime

    division-loop:
        copyfrom #partial
        sub #prime
        jumpn try-other-prime
        jump0 send-prime-to-outbox
        bump+ #counter
        copyfrom #partial
        sub #prime
        copyto #partial
        jump division-loop

    try-other-prime:
        bump+ #prime-candidate
        copyfrom #current
        copyto #partial
        copyfrom #zero
        copyto #counter
        jump is-prime

    send-prime-to-outbox:
        ; Update the counter.
        bump+ #counter

        ; Outbox the prime.
        copyfrom #prime
        outbox

        ; Counter contains the new value for current and partial.
        copyfrom #counter
        copyto #current
        copyto #partial

        ; The counter restarts from 0.
        copyfrom #zero
        copyto #counter

        ; Set another time the prime candidate to 2.
        copyfrom #two
        copyto #prime-candidate
        jump division-loop

    outbox-original:
        copyfrom #original
        outbox
        jump start

    outbox-current:
        copyfrom #current
        outbox
        jump start

.test
    contains outbox 2 2 5 3 5 2 3 19 2 13
