import sys
from generuj import hilbert3D
from modyfikuj import mod
from postscript import postscript

''' arguments '''
N = int(sys.argv[1])
S = int(sys.argv[2])
U = int(sys.argv[3])
D = int(sys.argv[4])
X = float(sys.argv[5])
Y = float(sys.argv[6])
Z = float(sys.argv[7])
FI  = float(sys.argv[8])
PSI = float(sys.argv[9])

''' List with points '''
lista = [(X,Y,Z)]

''' Generating and modifying '''
hilbert3D(lista, N)
lista = mod(lista, N, FI, PSI, U, D)

''' Print postscript file '''
postscript(lista,S)
