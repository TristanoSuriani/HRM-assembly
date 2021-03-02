alias #input 0

start:
    mov         rdi, banner         ; copy constant banner
    syscall     puts                ; call the "system" subroutine puts -> prints banner's content

    jump        prompt-loop

prompt-loop:
    mov         rdi, delimiter
    syscall     puts
    
    syscall     gets                ; call the "system" subroutine gets -> retrieves input from the user
    copyto      #input

    mov         rdi, quit           ; copy constant quit
    eq          #input              ; #input == quit?
    jumpn       echo                ; if not, go to echo

    jump       end

echo:
    mov         rdi, echo_prefix
    syscall     puts

    mov         rdi, #input
    syscall     puts

    mov         rdi, newline
    syscall     puts

    jump        prompt-loop

end:
    mov         rdi, banner         ; copy constant banner
    syscall     puts                ; call the "system" subroutine puts

    ret

banner:
    db          "HRM-Assembly by Tristano Suriani\n"

delimiter:
    db          "$hrma |> "

echo_prefix:
    db          "You said: "

newline:
    db          "\n"

quit:
    db          "quit"
