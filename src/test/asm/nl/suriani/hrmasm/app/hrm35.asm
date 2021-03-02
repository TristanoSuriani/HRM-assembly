; Duplicate removal
; Sends each unique letters to the outbox.

.setup
    push inbox B
    push inbox B
    push inbox A
    push inbox E
    push inbox A
    push inbox E
    push inbox E
    push inbox A
    push inbox C
    push inbox C

    set R14 0

.program

    alias #last 11
    alias #counter 12
    alias #current 13
    alias #zero 14

    prepare:
        copyfrom #zero
        copyto #last        ; at this position we can add the new unique value
        inbox
        copyto #current
        copyto* #last       ; added new unique value
        outbox

    start:
        copyfrom #zero      ; reset counter
        copyto #counter
        inbox
        copyto #current     ; updated current value

    duplicate-check-loop:
        copyfrom* #counter
        sub #current
        jump0 start         ; found a duplicate

        copyfrom #counter
        sub #last
        jump0 to-the-outbox  ; it's unique
        bump+ #counter
        jump duplicate-check-loop ; not finished testing yet


    to-the-outbox:
        bump+ #last
        copyfrom #current
        copyto* #last
        outbox
        jump start

.test
    contains outbox B A E C
