import base64

data=open("Innocent_Ascii","r").read()
data=base64.b64decode(data)
data=base64.b64decode(data)


open("img.png","w").write(data)


print "Securinets{V!g3n3r3_I5_P0w3rFuLL}"