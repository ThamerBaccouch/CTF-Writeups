import requests



host="http://34.250.176.49:8000/api/v1/challenges/attempt"
params={"session":"a4ba9d29-da92-4b9b-9844-663fdcdbbff1"}
possible=open("flags","r").readlines()
for i in possible:
	flag=i[:-1]
	print flag
	data={"challenge-id":22,
		"submission_":flag}

	requests.post(url=host,cookies=params,json=data)