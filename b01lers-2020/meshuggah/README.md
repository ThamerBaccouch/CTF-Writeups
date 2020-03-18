**## meshuggah**

Well this challenge is about predicting rand() result in c.

the program takes as a seed time(0) which will take the timestamp of the server as a seed to rand() and printf the very first 3 numbers of rand(). We are asked to predict the rand values 92 times to get the flag.

The solution is to kinda leak the time on the server and use it in a c code to predict the next values.
well my idea was to connect to the netcat service then get the time of the server from a web challenge in the response header after that we brute force the seed from time_from_web_challenge-10000 to time_from_web_challenge+10000 and see if we get a match on the first 3 values provided by the service.

when we get the seed we can simply generate all of the rest rand values and feed it to the netcat service.

you can check the rand generator and python script for this challenge.
