; Digit exploder
; Grab each number from the INBOX, and send its digits to the OUTBOX. For example, 123 becomes 1, 2, 3.

.setup
    push inbox 704
    push inbox 7
    push inbox 564
    push inbox 26

    set R9 0
    set R10 10
    set R11 100

.program
    alias #current 0
    alias #temp 1
    alias #one 2
    alias #h 3
    alias #d 4
    alias #u 5
    alias #previous-result 6
    alias #zero 9
    alias #ten 10
    alias #hundred 11

    start:
        copyfrom #zero
        copyto #u
        copyto #d
        copyto #h
        inbox
        copyto #current
        copyto #temp
        sub #ten
        jumpn outbox-from-u
        sub #hundred
        jumpn div-10
        jump div-100

    div-10:
        copyfrom #temp
        sub #ten
        jumpn outbox-from-d
        copyto #temp
        bump+ #d
        jump div-10

    div-100:
        copyfrom #temp
        sub #hundred
        jumpn outbox-from-h
        copyto #temp
        bump+ #h
        jump div-100

    outbox-from-u:
        copyfrom #current
        outbox
        jump start

    outbox-from-d:
        copyfrom #temp
        copyto #u
        copyfrom #d
        outbox
        copyfrom #u
        outbox
        jump start

    outbox-from-h:
        copyfrom #h
        outbox
        jump div-10

.test
    contains outbox 7 0 4 7 5 6 4 2 6
