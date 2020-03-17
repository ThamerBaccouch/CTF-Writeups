from pwn import *
import os
seed="1584196411"
#p=process("./meshuggah")
p=remote("pwn.ctf.b01lers.com",1003)
p.recvuntil("Meshuggah-")
f1=p.recvline()[:-1]
p.recvuntil("Meshuggah-")
f2=p.recvline()[:-1]
p.recvuntil("Meshuggah-")
f3=p.recvline()[:-1]

os.system("./gen "+seed+" "+f1+" "+f2+" "+f3+" >sub.txt")

a=open("sub.txt").readlines()
numbers=list()
for i in a:
    numbers.append(i[:-1])
for i in range(92):
    print p.recvuntil("like to buy?")
    p.sendline(numbers[i])

p.interactive()




























