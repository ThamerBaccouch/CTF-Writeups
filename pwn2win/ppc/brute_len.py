from pwn import *
import base64

path=["D","SDDW","SSDDWW","","DSSSSASDSDSSAA","DSSSSASSSSSSSDSDSDDDDDDDDDDDDDDDDDDDWWWWDDDWWDDDDDDDSSDWWDWAWWWDDDDDDDDDDSSSDDSSASSAAASSAAASSDDDDDDDDDDDDDDDWWWDDSSS","","SAAWAAASSAAAASSASSSSSSSDSDSDDDDDDDDDDDDDDDDDDDWWWWDDDWWDDDDDDDSSDWWDWAWWWDDDDDDDDDDSSSDDSSASSAAASSAAASSDDDDDSSSDDSDDDDDDSSSDDSDDWWWAAWWWWAAWDDWWWDDSSS","SAAWAAASSAA",""]
ans=[1,4,6,-1,12,90,-1,100,0,0]
done=[1,1,1,1,1,1,1,1,0,0]
cnt=9
choices=["A","S","D","W"]
success=open("success").read()
empty=open("empty").read()
bomb=open("bomb").read()
wall=open("wall").read()
context.log_level="error"


def brute_len(p,s,idx):
	global done
	global path
	for i in range(10):
		if i == idx:
			break
		if done[i] :
			for j in path[i]:
				p.sendline(j)
			p.sendline("ANS "+str(ans[i]))
			p.recvuntil("ROUND "+str(i+1+1)+"/10\n")[-50:]

	for j in path[idx]:
		p.sendline(j)
	p.sendline("ANS "+str(s))
	try:
		p.recvuntil("ROUND "+str(i+1+1)+"/10\n")[-50:]
		print "ANS :",s
		exit(0)
	except:
		c=0


bb=len(path[cnt-1])
for i in range(bb):
	p=remote("soundmaze.pwn2.win",31337)
	print "try :",(bb-i)
	brute_len(p,bb-i,cnt-1)
	p.close()




"""
def complete_done(p):
	global done
	global path
	for i in range(10):
		print i
		if done[i] :
			for j in path[i]:
				p.sendline(j)
			p.sendline("ANS "+str(ans[i]))
			print p.recvuntil("ROUND "+str(i+1+1)+"/10\n")[-50:]
		else:
			break



def brute(curr):
	stay=1
	okey=[]
	count=0
	print "CURR :",curr
	for choice in choices:
		p=remote("soundmaze.pwn2.win",31337)
		p.recvuntil("ROUND 1/10\n")
		complete_done(p)
		for j in curr:
			p.sendline(j)
			data=p.recvuntil("\n\n\n").strip()

		p.sendline(choice)
		data=p.recvuntil("\n\n\n").strip()
		open(str(4+count),"w").write(base64.b64decode(data))
		count+=1
		data=base64.b64decode(data)
		if data==success:
			print "SUCCESS ",choice
			print "solution :",curr+choice
			exit(0)
		elif data == empty:
			print "EMPTY ",choice
			okey.append(choice)
		elif data == bomb:
			print "BOMB :",choice
		elif data == wall:
			print "WALL :",choice
		else:
			print data
			try:
				print "---------"
				p.recv(8000)
				print "---------"
			except:
				c=0
			print "UNKNOWN :",choice
		p.close()
	for i in okey:
		brute(curr+i)


"""
