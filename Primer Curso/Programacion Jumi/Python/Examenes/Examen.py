 #Ejercicio 1
print("Ejercicio 1")
i=10
j=i
j=j*2
i=i-2
j=(j+i)*i*j
print(i,j)
#Ejercicio 2
i=10
j=i
j=(-2*j)/-2
a=True
print((a and i==j))
#Ejercicio 3
i=0
j=-i
i=-i
j=-j
if (i>0) and (i<3):
     i=i+2
     j=j+2
print(i*i+j*j)
#Ejercicio 9
print("Ejercicio 9")
i=0
v = [-1,0,0,0]
mida_v = len(v)
while (i<(mida_v-1))and (v[i]<0):
     print (i,v[i])
     i=i+1
     v[i]=v[i-1]+1
print (i,v[i])
#Ejercicio 10
print("Ejercicio 10")
i=0
v = [-1,0,0,0]
mida_v = len(v)
while (i<(mida_v-1))or (v[i]<=0):
     print (i,v[i])
     i=i+1
     v[i]=v[i-1]+1
