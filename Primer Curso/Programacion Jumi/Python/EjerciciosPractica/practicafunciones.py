# # Parte 1
# #-- 1 ----------------------------------
# def f(a):
#     s = 0
#     for i in a:
#         if i % 2 == 0:
#             s += i
#     return s
#
# x = [1,2,3,4]
# print(f(x))
# #-- 2 ----------------------------------
# def f(a):
#     for i in range(len(a)):
#         a[i] *= 2
#     return a
#
# x = [1,2,3]
# print(f(x))
#
# #-- 3 ----------------------------------
# def f(d):
#     s = 0
#     for k in d:
#         s += d[k]
#     return s
#
# x = {'a':1,'b':2}
# print(f(x))
#
# #-- 4 ----------------------------------
# def f(a):
#     r = []
#     for i in a:
#         if i > 2:
#             r.append(i)
#     return r
#
# x = [1,2,3,4]
# print(f(x))
#
# #-- 5 ----------------------------------
# def f(a):
#     for i in range(len(a)):
#         if a[i] % 2 != 0:
#             a[i] += 1
#     return a
#
# x = [1,2,3]
# print(f(x))
#
# #-- 6 ----------------------------------
# def f(d):
#     for k in d:
#         if d[k] % 2 == 0:
#             d[k] += 1
#     return d
#
# x = {'a':2,'b':3}
# print(f(x))
#
# #-- 7 ----------------------------------
# def f(a):
#     s = 0
#     for i in range(len(a)):
#         if i % 2 == 0:
#             s += a[i]
#         else:
#             s -= a[i]
#     return s
#
# x = [1,2,3,4]
# print(f(x))
#
# #-- 8 ----------------------------------
# def f(d):
#     c = 0
#     for k in d:
#         if len(d[k]) > 1:
#             c += 1
#     return c
#
# x = {'a':[1], 'b':[2,3]}
# print(f(x))
#
# #-- 9
# def f(a):
#     for i in range(len(a)):
#         for j in range(i+1,len(a)):
#             if a[i] > a[j]:
#                 a[i],a[j]=a[j],a[i]
#     return a
#
# x = [3,1,2]
# print(f(x))
#
# #-- 10
# def f(d):
#     s = 0
#     for k in d:
#         for v in d[k]:
#             s += v
#     return s
#
# x = {'a':[1,2], 'b':[3]}
# print(f(x))
#
# #-- 11
# def f(a,d):
#     for i in range(len(a)):
#         if a[i] in d:
#             a[i] = d[a[i]]
#     return a
#
# a = [1,2,3]
# d = {1:10,3:30}
# print(f(a,d))
#
# #-- 12
# def f(d):
#     r = {}
#     for k in d:
#         s = 0
#         for v in d[k]:
#             s += v
#         r[k] = s
#     return r
#
# x = {'a':[1,2],'b':[3,4]}
# print(f(x))
#
# #-- 13
# def f(a):
#     r = []
#     for i in a:
#         if i not in r:
#             r.append(i)
#     return r
#
# x = [1,2,1,3]
# print(f(x))
#
# #-- 14
# def f(d):
#     for k in d:
#         d[k] = [v*2 for v in d[k]]
#     return d
#
# x = {'a':[1,2],'b':[3]}
# print(f(x))
#
# #-- 15
# def f(a):
#     for i in range(len(a)):
#         if a[i] < 0:
#             a[i] = -a[i]
#     return a
#
# x = [-1,2,-3]
# print(f(x))
#
# #-- 16
# def f(d):
#     c = 0
#     for k in d:
#         for v in d[k]:
#             if v % 2 == 0:
#                 c += 1
#     return c
#
# x = {'a':[1,2],'b':[2,4]}
# print(f(x))
#
# #-- 17
# def f(a):
#     for i in range(len(a)):
#         for j in range(len(a)):
#             if i != j and a[i] == a[j]:
#                 a[j] = 0
#     return a
#
# x = [1,2,2,3]
# print(f(x))
#
# #-- 18
# def f(g):
#     visitado = {k:False for k in g}
#     res = []
#     for n in g:
#         if not visitado[n]:
#             pila=[n]
#             while pila:
#                 a=pila.pop()
#                 if not visitado[a]:
#                     visitado[a]=True
#                     res.append(a)
#                     for v in g[a]:
#                         if not visitado[v]:
#                             pila.append(v)
#     return res
#
# g = {'A':['B'],'B':['C'],'C':[]}
# print(f(g))

#-- 19
def f(g):
    grado = {k:0 for k in g}
    for k in g:
        for v in g[k]:
            grado[v]+=1
    return grado

g = {1:[2],2:[3],3:[]}
print(f(g))

#-- 20
def f(g):
    grado = {k:0 for k in g}
    for k in g:
        for v in g[k]:
            grado[v]+=1
    cola=[k for k in grado if grado[k]==0]
    res=[]
    while cola:
        a=cola.pop(0)
        res.append(a)
        for v in g[a]:
            grado[v]-=1
            if grado[v]==0:
                cola.append(v)
    return res

g = {1:[2],2:[3],3:[]}
print(f(g))

# #-- 21
# def f(d):
#     keys=list(d.keys())
#     for i in range(len(keys)):
#         for j in range(i+1,len(keys)):
#             if d[keys[i]][0] > d[keys[j]][0]:
#                 d[keys[i]],d[keys[j]]=d[keys[j]],d[keys[i]]
#     return list(d.values())
#
# x = {'a':[3],'b':[1],'c':[2]}
# print(f(x))
#
# #-- 22
# def f(a):
#     s=0
#     for i in range(len(a)):
#         for j in range(i,len(a)):
#             s+=a[j]
#     return s
#
# x = [1,2,3]
# print(f(x))
#
# #-- 23
# def f(d):
#     r={}
#     for k in d:
#         m=d[k][0]
#         for v in d[k]:
#             if v<m:
#                 m=v
#         r[k]=m
#     return r
#
# x = {'a':[3,1],'b':[5,4]}
# print(f(x))
#
# #-- 24
# def f(a):
#     for i in range(len(a)):
#         for j in range(i+1,len(a)):
#             if a[i] < a[j]:
#                 a[i],a[j]=a[j],a[i]
#     return a
#
# x = [1,3,2]
# print(f(x))
#
# #-- 25
# def f(g):
#     visitado={k:0 for k in g}
#     c=0
#     for n in g:
#         if visitado[n]==0:
#             pila=[n]
#             while pila:
#                 a=pila.pop()
#                 if visitado[a]==0:
#                     visitado[a]=1
#                     c+=1
#                     for v in g[a]:
#                         if visitado[v]==0:
#                             pila.append(v)
#     return c
#
# g = {1:[2],2:[],3:[]}
# print(f(g))
#
# #-- 26
# def f(d):
#     for k in d:
#         for i in range(len(d[k])):
#             if d[k][i]%2==0:
#                 d[k][i]*=2
#             else:
#                 d[k][i]-=1
#     return d
#
# x = {'a':[1,2],'b':[3,4]}
# print(f(x))
#
# #-- 27
# def f(a):
#     r=[]
#     for i in range(len(a)):
#         for j in range(len(a)):
#             if i!=j:
#                 r.append(a[i]+a[j])
#     return r
#
# x = [1,2,3]
# print(f(x))
#
# #-- 28
# def f(d):
#     s=0
#     for k in d:
#         for v in d[k]:
#             if v>2:
#                 s+=v
#             else:
#                 s-=v
#     return s
#
# x = {'a':[1,2],'b':[3,4]}
# print(f(x))
#
# #-- 29
# def f(g):
#     grado={k:0 for k in g}
#     for k in g:
#         for v in g[k]:
#             grado[v]+=1
#     return [k for k in grado if grado[k]==0]
#
# g = {1:[2],2:[3],3:[]}
# print(f(g))
#
# #-- 30
# def f(d):
#     r=[]
#     for k in sorted(d):
#         r.append(sum(d[k]))
#     return r
#
# x = {'b':[2,2],'a':[1,1]}
# print(f(x))
#
# #-- 31
# def f(a):
#     for i in range(len(a)):
#         for j in range(len(a)):
#             if a[i] < a[j]:
#                 a[i],a[j]=a[j],a[i]
#     return a
#
# x = [2,1,3]
# print(f(x))
#
# #-- 32
# def f(d):
#     for k in d:
#         d[k]=[v for v in d[k] if v%2==0]
#     return d
#
# x = {'a':[1,2,3],'b':[4,5]}
# print(f(x))
#
# #-- 33
# def f(a,d):
#     for i in range(len(a)):
#         if a[i] in d:
#             a[i]+=d[a[i]]
#         else:
#             a[i]-=1
#     return a
#
# a=[1,2,3]
# d={1:1,3:3}
# print(f(a,d))
#
# #-- 34
# def f(g):
#     grado={k:0 for k in g}
#     for k in g:
#         for v in g[k]:
#             grado[v]+=1
#     cola=[k for k in grado if grado[k]==0]
#     res=[]
#     while cola:
#         a=cola.pop()
#         res.append(a)
#         for v in g[a]:
#             grado[v]-=1
#             if grado[v]==0:
#                 cola.append(v)
#     return res
#
# g = {'A':['B','C'],'B':['D'],'C':['D'],'D':[]}
# print(f(g))
#
# #-- 35
# def f(d):
#     keys=list(d.keys())
#     for i in range(len(keys)):
#         for j in range(len(keys)):
#             if d[keys[i]][0] < d[keys[j]][0]:
#                 d[keys[i]],d[keys[j]]=d[keys[j]],d[keys[i]]
#     return d
#
# x = {'a':[3],'b':[1],'c':[2]}
# print(f(x))
#
# #-- 36
# def f(a):
#     s=0
#     for i in range(len(a)):
#         for j in range(len(a)):
#             if i<=j:
#                 s+=a[i]*a[j]
#     return s
#
# x=[1,2,3]
# print(f(x))
#
# #-- 37
# def f(g):
#     visitado={k:False for k in g}
#     orden=[]
#     for n in g:
#         if not visitado[n]:
#             pila=[n]
#             while pila:
#                 a=pila[-1]
#                 if not visitado[a]:
#                     visitado[a]=True
#                     for v in g[a]:
#                         if not visitado[v]:
#                             pila.append(v)
#                 else:
#                     pila.pop()
#                     if a not in orden:
#                         orden.append(a)
#     return orden
#
# g = {'A':['B','C'],'B':['D'],'C':['D'],'D':[]}
# print(f(g))
#
# #-- 38
# def f(d):
#     r={}
#     for k in d:
#         s=0
#         for v in d[k]:
#             for u in d[k]:
#                 s+=v*u
#         r[k]=s
#     return r
#
# x={'a':[1,2],'b':[3]}
# print(f(x))
#
# #-- 39
# def f(a):
#     for i in range(len(a)):
#         for j in range(i,len(a)):
#             if (a[i]+a[j])%2==0:
#                 a[i]+=1
#             else:
#                 a[j]+=1
#     return a
#
# x=[1,2,3]
# print(f(x))
#
# #-- 40
# def f(g):
#     grado={k:0 for k in g}
#     for k in g:
#         for v in g[k]:
#             grado[v]+=1
#     cola=[k for k in grado if grado[k]==0]
#     res=[]
#     while cola:
#         a=cola.pop(0)
#         res.append(a)
#         for v in g[a]:
#             for u in g:
#                 if v in g[u]:
#                     grado[v]-=1
#             if grado[v]==0 and v not in res and v not in cola:
#                 cola.append(v)
#     return res
#
# g = {1:[2,3],2:[4],3:[4],4:[]}
# print(f(g))