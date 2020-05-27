

a="GATCCGGCGCGCACTCTAACACCCGCACTGTCTACCTCTAACTACGTCTTCCTATCCAGCGCGCCCGCTTCCGCGCGATCAATACTGCTTCCTAACAATAGATCTTGCGTACACTACTTC"





count=0

def brute(b,picked,numbers):
	if len(picked)==4:
		num=numbers.split("|")[1:]
		dct={picked[0]:num[0],
		picked[1]:num[1],
		picked[2]:num[2],
		picked[3]:num[3]}
		c=""
		for j in split_by4(b):
			for k in j[::-1]:
				c+=dct[k]
		c=split_by8(c)
		flag=""
		for i in c:
			flag+=chr(int(i,2))
		print flag
		return
	for i in ['A','G','T','C']:
		if count == 24:
			return
		if i not in picked:
			for nm in ["|00","|01","|10","|11"]:
				if nm not in numbers:
					brute(b,picked+i,numbers+nm)


def split_by8(st):
	b=[]
	for i in range(0,len(st),8):
		b.append(st[i:i+8])
	return b

def split_by4(st):
	b=[]
	for i in range(0,len(st),4):
		b.append(st[i:i+4])
	return b

brute(a,"","")






