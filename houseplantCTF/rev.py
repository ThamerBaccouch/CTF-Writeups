import z3

a='\xc3\x92d\xc3\x9d\xc2\xbe\xc2\xa4\xc2\xa4\xc2\xbe\xc3\x99\xc3\xa0\xc3\xa5\xc3\x90c\xc3\x9d\xc3\x86\xc2\xa5\xc3\x8c\xc3\x88\xc3\xa1\xc3\x8f\xc3\x9c\xc2\xa6a\xc3\xa3'

flag=""
print len(a)
c="k33p_1t_in_pl41n"
inp=list()
for i in range(len(c)):
	inp.append(0)
for i in range(len(c)-2):
	inp[i+2]=ord(a[i])-ord(c[i])

inp[i]=ord(a[i])-ord(c[i])

for i in range()
print inp