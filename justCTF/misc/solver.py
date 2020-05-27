

f=open("inp2.txt","r").readlines()

inp=[]
for i in f:
	inp.append(i[:-1])

start=[]
visited=dict()
current=dict()

for i in inp:
	if i[0]!='_' and i[1]!='_':
		start.append(i)
	visited.update({i:False})

graph={}
for i in inp:
	children=[]
	for j in inp:
		if (j!=i):
			if(i[-1]==j[0]):
				children.append(j)
	graph.update({i:children})
mx=0
iterations=0
def dfs(node,counter,res):
	globals()["iterations"]+=1
	if(counter >=globals()['mx']):
		globals()['mx']=counter
	visited.update({node:True})
	if(counter == len(inp)):
		print("DONE !!! ")
		print (res)
		exit()
	else:
		print("mx :",globals()['mx']," -- inp:",len(inp)," iterations :",globals()["iterations"])
		print (res)
	for child in graph[node]:
		if(visited[child] == False):
			dfs(child,counter+1,res+child[1:])
	
	visited.update({node:False})


print graph

for s in start:
	dfs(s,1,s)



"""
def solve(res,l):
	counter[0]+=1
	print(counter[0])
	if(l == len(inp)):
		print ("done!!!!!!!!")
		print (res)
		exit()
	else:
		print (l)
		print (res)
	for i in inp:
		if used[i]==False:
			if(i[0]==res[-1]):
				used.update({i:True})
				solve(res+i[1:],l+1)
				used.update({i:False})
#print (inp)
#print (len(inp))

"""