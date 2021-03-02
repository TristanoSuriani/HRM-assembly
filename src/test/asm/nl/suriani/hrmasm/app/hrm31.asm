; String Reverse
; Takes each zero terminated string and sends its reverse to the outbox.
.setup

    push inbox I
    push inbox T
    push inbox 0
    push inbox Y
    push inbox O
    push inbox U
    push inbox 0
    push inbox N
    push inbox O
    push inbox W
    push inbox 0

    set R14 0

.program

    alias #zero 14
    alias #counter 13

    start:
        copyfrom #zero
        copyto #counter

    copy-loop:
        inbox
        jump0 countdown-loop
        copyto* #counter
        bump+ #counter
        jump copy-loop

    countdown-loop:
        bump- #counter
        copyfrom* #counter
        outbox
        copyfrom #counter
        jump0 start
        jump countdown-loop

.test
    contains outbox T I U O Y W O N
