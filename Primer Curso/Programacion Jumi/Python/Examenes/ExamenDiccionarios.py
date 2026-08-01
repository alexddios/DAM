print("Ej: 1")
d = {"a": 3, "b": 5, "c": 7}
d["d"] = d.get("a") + d.get("b")
d["a"] = d.get("a") + 1

print(d["d"])
print("d" in d)
print(d.get("e", d["a"]))
print("-" * 20)


print("Ej: 2")
d = {"x": 4, "y": 4, "z": 8}
d["x"] = d["x"] + 1
v = d.get("w", 2)
d["w"] = v + d["y"]

print(len(d))
print(d["w"])
print(4 in d.values())
print("-" * 20)


print("Ej: 3")
d = {"p": 2, "q": 6}
d["r"] = d["p"] + d["q"]
d["q"] = d["r"] - d["p"]
d["p"] = d["q"] - d["p"]

print(d["p"])
print(d["q"])
print(d["r"])
print("-" * 20)


print("Ej: 4")
d = {"a": 1, "b": 2, "a": 5}
d["b"] = d["a"] + d["b"]
d["a"] = d["b"] - d["a"]

print(d)
print(len(d))
print("-" * 20)


print("Ej: 5")
d = {"m": 7, "n": 3, "p": 7}
v = 7

for k in d:
    if d[k] == v:
        d[k] = d[k] - 2
    else:
        d[k] = d[k] + 1

for k in d:
    if d[k] % 2 == 1:
        print(k, d[k])
print("-" * 20)


print("Ej: 6")
d = {"a": 2, "b": 4, "c": 6}

for k in d.keys():
    print(k, d[k])
print("-" * 20)


print("Ej: 7")
d = {"u": 1, "v": 3, "w": 5}

for x in d.values():
    if x % 2 == 1:
        print(x)
print("-" * 20)


print("Ej: 8")
d = {"a": 10, "b": 20}

for k, v in d.items():
    print(k)
    print(v)
print("-" * 20)


print("Ej: 9")
d = {"a": [1, 2], "b": [3], "c": []}
d["a"].append(5)
d["b"] = d["b"] + [4]
print(d["a"])
print(d["b"])
print(len(d["c"]))
print("-" * 20)


print("Ej: 10")
v = [
    {"a": 2, "b": 3},
    {"a": 1, "b": 4},
    {"a": 2, "b": 1}
]

for d in v:
    print(d["a"] + d["b"])
print("-" * 20)


print("Ej: 11")
d = {"x": {"y": 3}, "z": {"y": 8}}
print(d["x"]["y"])
d["z"]["y"] = d["x"]["y"] + 2
print(d["z"]["y"])
print("-" * 20)


print("Ej: 12")
d = {"a": 4, "b": 7, "c": 2}
x = d.pop("b")
d["d"] = x - d["a"]
del d["c"]
print(x)
print(d)
print("-" * 20)


print("Ej: 13")
d = {"a": 1, "b": 2, "c": 3}
for k in list(d.keys()):
    if d[k] % 2 == 1:
        d[k + k] = d[k] + 10
print(d)
print("-" * 20)


print("Ej: 14")
v = ["a", "b", "a", "c", "b", "a"]
d = {}

for x in v:
    d[x] = d.get(x, 0) + 1

for k in d:
    print(k, d[k])
print("-" * 20)


print("Ej: 15")
v = [
    {"k": "a", "v": 2},
    {"k": "b", "v": 1},
    {"k": "a", "v": 3}
]
d = {}

for x in v:
    if x["k"] not in d:
        d[x["k"]] = []
    d[x["k"]].append(x["v"])

for k in d:
    print(k, d[k])
print("-" * 20)


print("Ej: 16")
d = {
    "a": {"x": [1, 2], "y": [3]},
    "b": {"x": [4], "y": [5, 6]}
}

for k in d:
    s = 0
    for p in d[k]:
        for x in d[k][p]:
            if x % 2 == 0:
                s += x
            else:
                s -= x
    print(k, s)
print("-" * 20)