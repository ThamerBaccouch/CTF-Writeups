**## DFV**

Well this one is pretty basic after some static analysis I found out that we have BOF with gets() the program xors our input with a variable in the stack then checks if it's equal to another variable on stack and gives the flag if the condition is satisfied.

with the BOF we have controle over those 2 variables inclunding our input.
so we chose 3 numbers that satisfy that condition.

you can check solver.py to see the script.
