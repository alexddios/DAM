#1
print("Ej 1:")
v = [7,2,9,4]
print(v[1],v[-2])
p = 2
print(v[p])
print("*"*20)
#2
print("Ej 2:")
v = [5,10,15,20]
t = 0
for p in range(len(v)):
    t += v[p]
print(t)
print("*"*20)
#3
print("Ej 3:")
v = [8,6,4]
for x in v:
    print(x-1)
for p in range (len(v)):
    v[p] +=2
print(v)
print("*"*20)
#4
print("Ej 4:")
v = [3,6,9,12,15]
w = v[2:5]
w[1] = 100
print(v)
print(w)
print("*"*20)
#5
print("Ej 5:")
g=[
    [2,4],
    [6,8]
]
print(g[1][0])
g[0][1] = g[0][0] + g[1][1]
print(g)
print("*"*20)
#6
print("Ej 6:")
v=[9,8,7,6,5]
for p in range(0,len(v),2):
    print(v[p])
for p in range(len(v)-1,-1,-2):
    print(v[p])
print("*"*20)
#7
print("Ej 7:")
v=[2,3,4,5]
p = 0

while p<len(v):
    if v[p]%2 ==0:
        v[p] +=1
    else:
        v[p] +=p
    p +=1
print(v)
print("*"*20)
#8
print("Ej 8:")
g =[
    [1,3,5],
    [2,4,6],
    [7,8,9]
]
t = 0
for p in range (len(g)):
    t+= g[p][0]
print(t)
u=0
for r in g:
    u+= r[2]
print(u)
print("*"*20)
#9
print("Ej 9:")
v = [3,6,9,12]
print(len(v))
v.append(len(v))
print(v)

v.pop((len(v)-2))
print(len(v),v)
print("*"*9)
#10
print("Ej 10: ")
v = [3,1,4,2]

for p in range(1,len(v)):
    x = v[p]
    q = p-1

    while q>=0 and v[q] > x:
        v[q+1] = v[q]
        q -=1
    v[q+1] = x

    if p==2:
        print(v)
print("*"*20)
#11
print("Ej 11: ")
v = [4,3,2,1]
for k in range(len(v)-1):
    for p in range(k+1,len(v)):
        if v[p] < v[k]:
            v[p],v[k] = v[k],v[p]
    print(k,v)
print("*"*20)
#12
print("Ej 12: ")
v = [1,2,3,4,5]
p=0

while p<len(v):
    if v[p] >3:
        v.pop(p)
    else:
        p+=1
print(v)
print("*"*20)
#13
print("Ej 13: ")
g=[
    [1,2,3],
    [4,5,6]
]
for p in range(len(g)):
    for q in range (1,len(g[0])):
        g[p][q] = g[p][q] + g[p][q-1]
print(g)
print("*"*20)
#14
print("Ej 14: ")
v = [5,1,4,2,3]
for k in range(len(v)-1,0,-1):
    for p in range(k):
        if v[p] > v[p+1]:
            v[p],v[p+1] = v[p+1],v[p]
    if k== 3:
        print(v)
print(v)
print("*"*20)