from z3 import *
import string 
from pwn import *
pp=string.digits
pp+=string.ascii_letters

a=[]
def verify(usr,pas):
	if len(usr)!=40:
		print "error :",len(usr)
		return False
	if len(pas)!=40:
		print "error :",len(pas)
		return False

	if (len(usr) <= 9):
		print "nope"
		return False
	if (len(pas) <= 29):
		print "nope"
		return False
	a=list()
	v5=0
	for i in range(40):
		a.append(0)
		a[i]= (ord(st[i])+i) ^ (20 * ord(pas[i]))
		a[i]%=256
		a[i]*=2
		a[i]%=256
		a[i]^=ord(usr[i])
		a[i]%=256
		for j in range(256):
			v5+= i * 8 * a[i]
			if( j>i):
				v5+=i*j
	print "0xc57359a"
	print hex(v5)
	print 0xc57359a == v5
	return 0xc57359a == v5
"""def calc(ch1, ch2,ind):
	return ind * (((st[ind]^(20* ord(ch2) - (ind%2)))*2)^ord(ch1))

def go():
	usr=list("hIHI0MD61WTe6sHSqYOhh2SSs3ooLtNXzhVtYVz8")
	pas=list("hIHI0MD61WTe6sHSqYOhh2SSs3ooLtNXzhVtYVz8")
	final=list()
	cur=0xc51d59a
	goal=0xc57359a
	for i in range(40):
		if cur >=goal:
			break
		mx=calc(usr[i],pas[i],i)
		new_usr=usr[i]
		new_pas=pas[i]
		for printable in pp:
			a=calc(usr[i],printable,i)
			b=calc(printable,pas[i],i)
			if(mx < a):
				new_usr=usr[i]
				new_pas=printable
				cur+=a-mx
				mx=a
				print "found"
			if (mx < b):
				new_usr=printable
				new_pas=pas[i]
				cur+=b-mx
				mx=b
				print "found "
		usr[i]=new_usr
		pas[i]=new_pas
	print "cur :", cur
	print "goal :",goal


def go2():
	final=list()
	goal=0xc57359a
	for ind in range(40):
		raw=list()
		for i in range(32,128):
			for j in range(32,128):
				res=calc(chr(i),chr(j),ind)
				
				raw.append(res)
		final.append(sorted(list(set(raw))))
	cnt=0
	for i in range(40):
		print final[-2]
	print cnt
"""

st="45df3e956218fabd49612b55e5593ce017f99ef9"
lines=open("./inp","r").readlines()
cnt=0
for ch in lines:

	v41= [ int(i,10) for i in ch[1:-2].split(",")]
	
	s = Solver()
	
	v5=0
	for j in range(40):
		globals()['a%i'%j] = BitVec('a%i'%j,32)
		globals()['b%i'%j] = BitVec('b%i'%j,32)
	for i in range(0,40):
		s.add( Or( And(globals()['a%i'%i] >= 48,globals()['a%i'%i] <= 57), Or(And(globals()['a%i'%i] >= 65,globals()['a%i'%i] <= 90),And(globals()['a%i'%i] >= 97,globals()['a%i'%i] <= 122)) ))
		s.add( Or( And(globals()['b%i'%i] >= 48,globals()['b%i'%i] <= 57), Or(And(globals()['b%i'%i] >= 65,globals()['b%i'%i] <= 90),And(globals()['b%i'%i] >= 97,globals()['b%i'%i] <= 122)) ))

	for i in range(0,40):

		s.add(((v41[i] ^ globals()['b%i'%i])) == (( ( ( (ord(st[i])+i)^(20* globals()['a%i'%i] -(i%2))  )*2 ) )))
	print "cnt :",cnt," - ",s.check()
	cnt+=1


	if s.check() == sat:
		flag = ""
		modl = s.model()
		user=list()
		pas=list()
		for i in range(40):
			user.append(modl[globals()['b%i'%i]])
			pas.append(modl[globals()['a%i'%i]])
		usr=""
		pa=""
		for i in str(user)[1:-1].split(","):
			usr+=chr(int(i,10))
		print usr
		for i in str(pas)[1:-1].split(","):
			pa+=chr(int(i,10))
		print pa
		print "#######################################"
		if(verify(usr,pa)):
			print usr
			print pa
			exit()
"""
		con=remote("192.168.125.17",6633)
		for i in usr:
			con.send(i)
		con.send("\n")
		print con.recvuntil("password")
		con.send(pa)
		
		con.recvline()
		print con.recvline()
		
		con.close()
"""
			


