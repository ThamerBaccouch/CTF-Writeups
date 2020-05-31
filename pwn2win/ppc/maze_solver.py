from pwn import *
import base64

path=["D","SDDW","SSDDWW","","DSSSSASDSDSSAA","DSSSSASSSSSSSDSDSDDDDDDDDDDDDDDDDDDDWWWWDDDWWDDDDDDDSSDWWDWAWWWDDDDDDDDDDSSSDDSSASSAAASSAAASSDDDDDDDDDDDDDDDWWWDDSSS","","SAAWAAASSAAAASSASSSSSSSDSDSDDDDDDDDDDDDDDDDDDDWWWWDDDWWDDDDDDDSSDWWDWAWWWDDDDDDDDDDSSSDDSSASSAAASSAAASSDDDDDSSSDDSDDDDDDSSSDDSDDWWWAAWWWWAAWDDWWWDDSSS","SAAWAAASSAA",""]
ans=[1,4,6,-1,12,90,-1,100,11,0]
done=[1,1,1,1,1,1,1,1,1,0]
choices=["A","S","D","W"]
success=open("success").read()
empty=open("empty").read()
bomb=open("bomb").read()
wall=open("wall").read()
context.log_level="error"


def complete_done(p):
	global done
	global path
	for i in range(10):
		if done[i] :
			for j in path[i]:
				p.sendline(j)
			p.sendline("ANS "+str(ans[i]))
			p.recvuntil("ROUND")
			p.recvuntil("/10\n")
		else:
			break

def brute(curr):
	stay=1
	okey=[]
	count=0
	print "###########"
	print "CURR :",curr
	print "###########"
	for choice in choices:
		p=remote("soundmaze.pwn2.win",31337)
		p.recvuntil("ROUND 1/10\n")
		complete_done(p)
		p.sendline("ANS 0")
		p.interactive()
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
			print "UNKNOWN :",choice
		p.close()
	for i in okey:
		brute(curr+i)

brute("")

