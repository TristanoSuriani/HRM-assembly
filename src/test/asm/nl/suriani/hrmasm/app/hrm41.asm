; Sorting Floor
; For each 0 terminated string sort elements and send to outbox.

.setup
    push inbox 83
    push inbox 35
    push inbox 64
    push inbox 0

    push inbox C
    push inbox L
    push inbox O
    push inbox U
    push inbox D
    push inbox 0

    push inbox 26
    push inbox 73
    push inbox 54
    push inbox 53
    push inbox 69
    push inbox 31
    push inbox 69
    push inbox 11
    push inbox 91
    push inbox 70
    push inbox 0

    push inbox 98
    push inbox 0

    set R24 0

.program
    ; Put all elements on the floor.
    ; For each elements:
    ; is the next element smaller? swap, flag change. Otherwise leave unchanged.
    ; Is change flagged? restart sorting. Outbox everything otherwise.

    alias #counter+1 17
    alias #current 18
    alias #next 19
    alias #counter 20
    alias #last 21
    alias #swap 22
    alias #changed 23
    alias #zero 24

    start:
        copyfrom #zero
        copyto #counter
        copyto #counter+1
        copyto #last
        copyto #changed
        bump- #last
        bump+ #counter+1
        jump put-string-on-the-floor

    put-string-on-the-floor:
        inbox
        copyto #current
        jump0 prepare-to-sort-loop
        bump+ #last
        copyfrom #current
        copyto* #last
        jump put-string-on-the-floor

    prepare-to-sort-loop:
        copyfrom #zero
        copyto #counter
        copyto #counter+1
        bump+ #counter+1
        jump sort-loop

    sort-loop:
        copyfrom #counter
        sub #last
        jump0 is-sorted
        copyfrom* #counter
        copyto #current

        copyfrom* #counter+1
        copyto #next
        sub #current    ; if next - current < 0 swap, otherwise leave unchanged
        jumpn swap-current-and-next
        bump+ #counter
        bump+ #counter+1
        jump sort-loop

    swap-current-and-next:
        copyfrom #next
        copyto* #counter
        copyfrom #current
        copyto* #counter+1
        bump+ #changed
        jump sort-loop

    is-sorted:
        copyfrom #changed
        jump0 prepare-to-the-outbox
        copyfrom #zero
        copyto #changed
        jump prepare-to-sort-loop

    prepare-to-the-outbox:
        copyfrom #zero
        copyto #counter
        copyto #counter+1
        bump+ #counter+1
        jump to-the-outbox

    to-the-outbox:
        copyfrom* #counter
        copyto #current
        outbox
        copyfrom #counter
        sub #last
        jump0 start
        bump+ #counter
        bump+ #counter+1
        jump to-the-outbox

.test
    contains outbox 35 64 83 C D L O U 11 26 31 53 54 69 69 70 73 91 98
