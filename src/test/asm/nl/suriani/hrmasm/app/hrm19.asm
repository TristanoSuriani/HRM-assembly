; Countdown
; Takes each number from the inbox and send it to the outbox plus all the numbers down to (or up to) 0

.setup
    push inbox 3
    push inbox -3
    push inbox 0
    push inbox -4

.program
    start:
        inbox
        copyto 0        ; r0 contains the current element

    countdown:
        copyfrom 0
        jumpn element-is-negative
        jump0 element-is-0
        jump element-is-positive

    element-is-positive:
        outbox
        copyfrom 0
        bump- 0
        jump countdown

    element-is-negative:
        outbox
        copyfrom 0
        bump+ 0
        jump countdown

    element-is-0:
        copyfrom 0
        outbox
        jump start

.test
    contains outbox 3 2 1 0 -3 -2 -1 0 0 -4 -3 -2 -1 0
