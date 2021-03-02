; Alphabetizer
; The inbox contains exactly two words. Determina which one comes first and send only that one to the outbox.

.setup
    push inbox U
    push inbox N
    push inbox Z
    push inbox I
    push inbox P
    push inbox 0
    push inbox U
    push inbox N
    push inbox T
    push inbox I
    push inbox E
    push inbox 0

    set R23 0
    set R24 10

.program
    alias #result-check 19
    alias #letter1 20
    alias #counter1 21
    alias #counter2 22
    alias #zero 23
    alias #ten 24

    start-first-word:
        copyfrom #zero
        copyto #counter1
        jump first-word

    start-second-word:
        copyfrom #ten
        copyto #counter2
        jump second-word

    start-check:
        copyfrom #zero
        copyto #counter1
        copyfrom #ten
        copyto #counter2
        jump check

    first-word:
        inbox
        copyto* #counter1
        jump0 start-second-word
        bump+ #counter1
        jump first-word

    second-word:
        inbox
        copyto* #counter2
        jump0 start-check
        bump+ #counter2
        jump second-word

    check:
        copyfrom* #counter1
        copyto #letter1
        copyfrom* #counter2
        sub #letter1
        copyto #result-check
        bump+ #counter1
        bump+ #counter2
        copyfrom #result-check
        jump0 check             ; same word so far
        jumpn second-to-the-outbox     ; second comes first
        jump first-to-the-outbox

    first-to-the-outbox:
        copyfrom #zero
        copyto #counter1
        jump to-the-outbox

    second-to-the-outbox:
        copyfrom #ten
        copyto #counter1

    to-the-outbox:
        copyfrom* #counter1
        jump0 end
        outbox
        bump+ #counter1
        jump to-the-outbox

    end:

.test
    contains outbox U N T I E
