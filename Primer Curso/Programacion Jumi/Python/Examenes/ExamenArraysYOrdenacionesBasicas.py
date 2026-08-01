# 1
print("Ej 1")
v = [3,1,4,1,5]
print([v[0],v[-1]])
p=1
print(v[p],p)
print("-"*20)

#2
print("Ej 2")
v=[10,20,30,40]
t=0
for p in range(len(v)):
    if p % 2 == 0:
        t+=v[p]
    else:
        t-=v[p]
print(t)
print("-"*20)

#3
print("Ej 3")
v=[2,4,6]
for x in v:
    print(x//2)
for p in range(len(v)):
    v[p] +=p
print(v)
print("-"*20)

#4
print("Ej 4")
v = [1,2,3,4,5]
w = v[1:4]
w[0] = 99
print(v)
print(w)
print(v[-2],w[-1])
print("-"*20)

#5
print("Ej 5")
g = [
    [1,2,3],
    [4,5,6]
]
print(g[0][2])
g[1][0] = g[0][1] + g[1][2]
print(g[1])
print("-"*20)

#6
print("Ej 6")
v = [5,4,3,2,1]
for p in range(0,len(v),2):
    print(p,v[p])
for p in range(len(v)-1,-1,-2):
    print(p,v[p])
print("-"*20)

#7
print("Ej 7")
v = [1,3,5,7]
p = 0
while p < len(v):
    if v[p] % 3 == 0:
        v[p] += 10
    else:
        v[p] += p
    p += 1
print(v)
print("-"*20)

#8
print("Ej 8")
g = [
    [2,1,0],
    [3,4,5],
    [6,7,8]
]
t=0
for p in range(len(g)):
    t += g[p][p]
print(t)


u =0
for r in g:
    u += r[1]
print(u)
print("-"*20)

#9
print("Ej 9")
r = [0,0,0]
g = [r]*3

g[0][1]=9
print(g[1])

g[2][2] = 7
print(g)
print("-"*20)

#10
print("Ej 10")
v = [4,1,3,2]
for p in range(1,len(v)):
    x = v[p]
    q = p-1
    while q >= 0 and v[q] > x:
        v[q+1] = v[q]
        q -= 1
    v[q+1] = x
    if p in (1,3):
        print(p,v)
print("-"*20)

#11
print("Ej 11")
v = [3,2,1,4]
n = len(v)

for k in range(n-1,0,-1):
    f = False
    for p in range(k):
        if v[p] > v[p+1]:
            f = True
            v[p],v[p+1] = v[p+1],v[p]
    print(k,v,f)
    if not f:
        break
print("-"*20)

#12
print("Ej 12")
v = [5,1,4,2]
c = 0
for p in range (len(v)-1):
    for q in range(p+1,len(v)):
        if v[q]<=v[p]:
            v[p],v[q] = v[q],v[p]
            c+=1
    if p % 2 == 0:
        print(p,v,c)
print("final",v,c)
print("-"*20)

#13
print("Ej 13")
v = [1,2,3,4,5]
p=0

while p < len(v):
    if v[p] % 2 == 0:
        v.pop(p)
    else:
        v[p]*=2
        p+=1
print(v)
print("-"*20)

#14
print("Ej 14")
g = [
    [1,2,3],
    [4,5,6]
]
for p in range(len(g)):
    for q in range(1,len(g[0])):
        g[p][q] += g[p][q-1]
print(g[0])
print(g[1])
print("-"*20)

#15
print("Ej 15")
v = [2,5,3,1,4]
c = 0

for k in range(len(v)-1,0,-1):
    for p in range(k):
        if v[p] > v[p+1]:
            v[p],v[p+1] = v[p+1],v[p]
            c+=1
    if k in (4,2,1):
        print(k,v,c)
print("done",v)

print("-"*20)