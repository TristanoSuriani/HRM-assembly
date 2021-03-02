; Exclusive lounge
; Takes each each pair of elements from the inbox and sends to the outbox:
; 0 if they have the same sign
; 1 if they have a different sign

.setup
    push inbox 7
    push inbox 6
    push inbox -4
    push inbox 6
    push inbox -5
    push inbox -9
    push inbox -1
    push inbox 6
    set R4 0
    set R5 1

.program
; The idea of this code is that we can check if two numbers have the same sign by comparing them both with 0.

    start:
        ; ----------------------------------
        inbox
        copyto 0
        inbox
        copyto 1

    compare-r0-with-0:
        copyfrom 4          ; r4 contains 0
        sub 0
        copyto 2            ; if r2 is negative, r0 is positive

    compare-r1-with-0:
        copyfrom 4
        sub 1
        copyto 3            ; if r3 is negative, r1 is positive

    if_r0_is_positive_then:
        copyfrom 2
        jumpn r0-is-positive
    else1:
        jump r0-is-negative

    r0-is-positive:
    if_r1_is_positive_then1:
        copyfrom 3
        jumpn same_sign
        jump different_sign

   r0-is-negative:
   if_r1_is_positive_then2:
        copyfrom 3
        jumpn different_sign
        jump same_sign

   same_sign:
        copyfrom 4
        outbox
        jump start

   different_sign:
        copyfrom 5
        outbox
        jump start

.test
    contains outbox 0 1 0 1
