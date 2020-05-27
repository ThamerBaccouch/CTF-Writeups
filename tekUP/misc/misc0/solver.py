
#!/usr/bin/python
import math
import string


p=list(string.ascii_letters[26:])
inp=['I', 'T', 'A', 'E', 'A', 'M', 'K', 'I', 'T', 'E', 'K', 'X', 'E', 'S', 'O', 'O', 'K']

possible=[]
for i in p:
	row=[]
	row.append(chr( ( ( (ord(i)-64) +1)%26) +64))
	row.append(chr( ( ( (ord(i)-64) +2)%26) +64))
	row.append(chr( ( ( (ord(i)-64) +6)%26) +64))
	row.append(chr( ( ( (ord(i)-64) +24)%26) +64))
	possible.append(row)


for i in range(len(possible)):
	print p[i], " " ,possible[i]



def dp(current,pos,inp,counter,possible,p):
	if counter ==len(inp):
		cur=[]
		for i in current:
			cur.append(i)
		cur[5]='{'
		cur[-1]='}'
		print "".join([k for k in cur])
		print "\n"
		return
	a=pos
	a%=4
	fact=[1,2,6,24]

	cur1=[]
	cur2=[]
	for i in current:
		cur1.append(i)
		cur2.append(i)
	cnt=0
	for i in range(len(possible)):
		if(inp[counter] == possible[i][a]):
			cur1.append(p[i])

	cur2.append(inp[counter])

	dp(cur1,a+1,inp,counter+1,possible,p)
	dp(cur2,pos,inp,counter+1,possible,p)

current=[]
dp(current,0,inp,0,possible,p)
# isaca{}
#['I', 'T', 'A', 'E', 'A', 'M', 'K', 'I', 'T', 'E', 'K', 'X', 'E', 'S', 'O', 'O', 'K']









"""

cnt=0	
voyelle=['A','E','I','O','U','Y']
res=[]
for i in inp:
	if i not in voyelle:
		if cnt == 4:
			cnt=0
		counter=0
		for pos in possible:
			if( i == pos[cnt]):
				res.append(p[counter])
				cnt+=1
				break
			counter+=1
	else:
		res.append(i)
print inp
print res


pr=""
for i in range(len(inp)):
	if inp[i] in voyelle:
		pr+=inp[i]
	else:
		pr+=res[i]


print pr
"""