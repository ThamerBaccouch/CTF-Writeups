from z3 import *

st="45df3e956218fabd49612b55e5593ce017f99ef9"

s = Solver()
aa=117
v5=0
for j in range(40):
	globals()['a%i'%j] = BitVec('a%i'%j,32)
	globals()['b%i'%j] = BitVec('b%i'%j,32)
for i in range(0,40):
	#s.add( Or( And(globals()['a%i'%i] >= 48,globals()['a%i'%i] <= 57), Or(And(globals()['a%i'%i] >= 65,globals()['a%i'%i] <= 90),And(globals()['a%i'%i] >= 97,globals()['a%i'%i] <= 122)) ))
	#s.add( Or( And(globals()['b%i'%i] >= 48,globals()['b%i'%i] <= 57), Or(And(globals()['b%i'%i] >= 65,globals()['b%i'%i] <= 90),And(globals()['b%i'%i] >= 97,globals()['b%i'%i] <= 122)) ))

	s.add(globals()['b%i'%i] >= 32)
	s.add(globals()['b%i'%i] <= 127)
	s.add(globals()['a%i'%i] >= 32)
	s.add(globals()['a%i'%i] <= 127)
for j in range(37):
	s.add((aa  == ( ( ((ord(st[j])+j)^(20*globals()['a%i'%j]-(j%2)) )*2) ^ globals()['b%i'%j]) %256))

print s.check()

res=0
goal=0xc57359a
for i in range(40):
	for j in range(256):
		if j > i:
			res+=i*j

goal=(goal-res)/2048
for i in range(37):
	goal-=i*aa
print goal
s1=Solver()
x1=Int('x1')
x2=Int('x2')
x3=Int('x3')
s1.add(x1 * 37 + x2*38 + x3*39 == goal)
s1.add(x1 >0)
s1.add(x2 >0)
s1.add(x3 == 62)
print s1.check()
print s1.model()
s.add((111== ( ( ((ord(st[37])+37)^(20*globals()['a%i'%37]-(37%2)) )*2) ^ globals()['b%i'%37]) %256))
s.add((115 ==   ( ( ((ord(st[38])+38)^(20*globals()['a%i'%38]-(38%2)) )*2) ^ globals()['b%i'%38]) %256))
s.add((62 == ( ( ((ord(st[39])+39)^(20*globals()['a%i'%39]-(39%2)) )*2) ^ globals()['b%i'%39]) %256))

s.add(globals()['a%i'%39]==0)
s.add(globals()['b%i'%39]==0)
print s.check()
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
		a[i]= (ord(st[i])+i) ^ (20 * ord(pas[i])-(i%2))
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

#verify("gUs31M1ykwYg3EoW7UO9s7qyuEAia1YGEqGyEcYV","6Uxdt9nPIpbd1Yf28xd201wdf1DxPbtqwXAX0THd")
verify("7mc3yMy9COAgc5WG7Uw937A9um1iaY9w5qOy5glp","4pRdi9cHh5UDS7ElXxw2x1qlFj28PAh7Yx4Xf6bD")