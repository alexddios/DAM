# #16
print("Ej 16")
v = [7, 0, 7, 1, 7, 2]
print(v[0], v[2], v[-2])
print(v[1:5:2])
v[3] = v[0] + v[5]
print(v)
print("-"*20)

#17
print("Ej 17")
v = [2, 3, 5, 7, 11, 13]
t = 0
for p in range(len(v)):
    if p % 3 == 0:
        t += v[p] * 2
    else:
        t -= v[p]
print(t)
print("-"*20)

#18
print("Ej 18")
v = [8, 6, 4, 2, 0]
for x in v:
    print(x // 3, x % 3)
for p in range(1, len(v), 2):
    v[p] -= p
print(v)
print("-"*20)
#
# #19
# print("Ej 19")
# v = [10, 20, 30, 40, 50]
# w = v[:]
# w[1:4] = [99, 98, 97]
# print(v)
# print(w)
# print(v[::2], w[::-2])
# print("-"*20)
#
# #20
# print("Ej 20")
# g = [
#     [0, 1, 2, 3],
#     [4, 5, 6, 7]
# ]
# print(g[1][2], g[0][0])
# g[0][3] = g[1][0] + g[1][1]
# print(g[0])
# print("-"*20)
#
# #21
# print("Ej 21")
# v = [9, 8, 7, 6, 5, 4]
# for p in range(0, len(v), 3):
#     print(p, v[p])
# for p in range(len(v)-2, -1, -3):
#     print(p, v[p])
# print("-"*20)
#
#22
print("Ej 22")
v = [12, 15, 18, 21, 24, 27]
p = 0
while p < len(v):
    if v[p] % 4 == 0:
        v[p] //= 2
    elif v[p] % 3 == 0:
        v[p] += p
    else:
        v[p] -= 1
    p += 1
print(v)
print("-"*20)

#23
print("Ej 23")
g = [
    [3, 1, 4],
    [1, 5, 9],
    [2, 6, 5]
]
diag = 0
anti = 0
for p in range(len(g)):
    diag += g[p][p]
    anti += g[p][len(g)-1-p]
print(diag, anti)
scol = 0
for r in g:
    scol += r[0]
print(scol)
print("-"*20)

#24
print("Ej 24")
r = [0, 1, 2]
g = [r[:] for _ in range(3)]
g[0][1] = 99
print(g[1])
g[2] = g[2] + [7]
print(g)
print("-"*20)

#25
print("Ej 25")
v = [9, 3, 6, 1, 5, 2]
# Inserción con cortes intermedios
for p in range(1, len(v)):
    x = v[p]
    q = p - 1
    while q >= 0 and v[q] > x:
        v[q+1] = v[q]
        q -= 1
    v[q+1] = x
    if p in (2, 4, 5):
        print(p, v[:])
print("-"*20)

#26
print("Ej 26")
v = [4, 3, 2, 1, 0]
n = len(v)
for k in range(n-1, 0, -1):
    hubo_swap = False
    for p in range(k):
        if (p % 2 == 0 and v[p] < v[p+1]) or (p % 2 == 1 and v[p] > v[p+1]):
            v[p], v[p+1] = v[p+1], v[p]
            hubo_swap = True
    print(k, v, hubo_swap)
    if not hubo_swap:
        break
print("-"*20)

#27
print("Ej 27")
v = [6, 2, 5, 3, 4, 1]
c = 0
for p in range(len(v)-1):
    m = p
    for q in range(p+1, len(v)):
        if v[q] < v[m]:
            m = q
    if m != p:
        v[p], v[m] = v[m], v[p]
        c += 1
    if p % 2 == 1:
        print(p, v, c)
print("final", v, c)
print("-"*20)

#28
print("Ej 28")
v = [1, 2, 3, 4, 5, 6]
p = 0
while p < len(v):
    if v[p] % 2 == 0:
        v.pop(p)
    else:
        v[p] = v[p] ** 2
        p += 1
print(v)
print("-"*20)

#29
print("Ej 29")
g = [
    [1, 1, 1, 1],
    [2, 2, 2, 2],
    [3, 3, 3, 3]
]
# Prefijos por fila
for i in range(len(g)):
    for j in range(1, len(g[0])):
        g[i][j] += g[i][j-1]
print(g[0])
print(g[1])
print(g[2])
print("-"*20)

#30
print("Ej 30")
v = [5, 2, 9, 1, 5, 6]
c = 0
# Burbuja clásica contando swaps y mostrando ciertos pasos
for k in range(len(v)-1, 0, -1):
    for p in range(k):
        if v[p] > v[p+1]:
            v[p], v[p+1] = v[p+1], v[p]
            c += 1
    if k in (5, 3, 1):
        print(k, v[:], c)
print("done", v, c)
print("-"*20)