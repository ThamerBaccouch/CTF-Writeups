from z3 import *
import string 


pp=string.ascii_letters+string.digits
for ch in range(1):
	s = Solver()

	for j in range(40):
		globals()['a%i'%j] = BitVec('a%i'%j,32)
		globals()['a%i'%j] = Int("a"+str(j))
	globals()['v5'] = BitVec('v5',32)
	for i in range(0,40):
		s.add(globals()['a%i'%i] != 0)
		s.add(globals()['a%i'%i] >= 0)
		s.add(globals()['a%i'%i] < 256)

	s.add(Sum([i*globals()['a%i'%i] for i in range(40)])== 88817)

count =0
while s.check() == sat:
	flag = list("0"*40)
	for i in range(40):
		flag[i]=s.model()[globals()['a%i'%i]]
	print flag
	count +=1
	s.add(globals()['a%i'%39] != s.model()[globals()['a%i'%39]])


	"""
	for j in range(40):
		flag += chr(modl[globals()['a%i'%j]].as_long())
	print "found: ",flag
	"""
	
"""
s = Solver()
length = i 
for j in range(length):
	globals()['a%i'%j] = BitVec('a%i'%j,32)
for j in range(length):
	s.add(globals()['a%i'%j] >= 32)
	s.add(globals()['a%i'%j] <= 127)
x = ((globals()['a%i'%3] ^ 0x1337) + 6221293)
for j in range(length):
	x += ((x ^ globals()['a%i'%j])%0x539)
s.add(x == 6235464)
if s.check() == sat:
	flag = ""
	modl = s.model()
	for j in range(length):
		flag += chr(modl[globals()['a%i'%j]].as_long())
	print "found: ",flag
"""