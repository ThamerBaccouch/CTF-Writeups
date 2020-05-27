

def searchForFlag(value):
	for key in _dicts:
		for key2 in _dicts[key]:
			if int(_dicts[key][key2],2) == value:
				res=[] 
				res.append(chr(int(key2,2)))
				res.append(key)
				return res

inp=open("aaa.txt","r").readlines()

final=0b101001101

for i in range(len(inp)):
	inp[i]=inp[i][:-1]

_dicts={}
for i in range(len(inp)):
	if len(inp[i].split(",")) == 2 :
		if inp[i].split(',')[1] == "start":

			start_val=inp[i].split(",")[0]
			_dict={}
		else:
			key=inp[i].split(",")[0]
			value=inp[i].split(",")[1]
			_dict.update({key:value})
	elif inp[i] == "end":
		_dicts.update({start_val:_dict})

flag=""
while True:
	try:
		res=searchForFlag(final)
		flag+=res[0]
		final=int(res[1],2)
	except:
		print flag[::-1]
		exit()