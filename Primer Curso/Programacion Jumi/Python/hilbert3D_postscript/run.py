import os
os.system("python main.py 5 500 250 70 0 0 0 32 -38 > pics/default.ps")
#                         N  S   U  D  X Y Z PHI PSI 
'''
Written in python 2.7

Example comand with arguments

Args:
1. N - number of iterations (!:with 8 .ps file reaches to 580MB)
2. S - Postscript file size (SxS)
3. U - length of edges
4. D - distance from observer
(5,6,7) is X,Y,Z - starting point
8. PHI - angle of rotation 0Y
9. PSI - angle of rotation 0X
'''
