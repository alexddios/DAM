''' Generate list '''

def krok(tab, v):
	tab.append( (tab[-1][0]+v[0], tab[-1][1]+v[1], tab[-1][2]+v[2]) )

def wyk_krok(lista,v,ile, w0, w1, w2, w3, w4, w5, w6, w7 ):
	term ={	0 : w0,
		1 : w1,
		2 : w2,
		3 : w3,
		4 : w4,
		5 : w5,
		6 : w6,
		7 : w7}	

	st = term[ile]
	nd = term[ile+1]
	vec = ( v[nd][0]-v[st][0], v[nd][1]-v[st][1], v[nd][2]-v[st][2] )
	krok(lista, vec)
	return ile+1

# main mechanism 
def gen(lista, sr, n, _0, _1, _2, _3, _4, _5, _6, _7 ):
	v = [	( 0, 0, 0 ),
		( 0, 0, 1 ),
		( 0,-1, 1 ),
		( 0,-1, 0 ),
		( 1,-1, 0 ),
		( 1,-1, 1 ),
		( 1, 0, 1 ),
		( 1, 0, 0 )]
	
	if n == 0:
		return

	ile = 0
	gen(lista, lista[-1], n-1, _0, _3, _4, _7, _6, _5, _2, _1)
	ile = wyk_krok(lista,v,ile, _0, _1, _2, _3, _4, _5, _6, _7 )
	gen(lista, lista[-1], n-1, _0, _7, _6, _1, _2, _5, _4, _3)
	ile = wyk_krok(lista,v,ile, _0, _1, _2, _3, _4, _5, _6, _7 )
	gen(lista, lista[-1], n-1, _0, _7, _6, _1, _2, _5, _4, _3)
	ile = wyk_krok(lista,v,ile, _0, _1, _2, _3, _4, _5, _6, _7 )
	gen(lista, lista[-1], n-1, _2, _3, _0, _1, _6, _7, _4, _5) 
	ile = wyk_krok(lista,v,ile, _0, _1, _2, _3, _4, _5, _6, _7 )#### srodek
	gen(lista, lista[-1], n-1, _2, _3, _0, _1, _6, _7, _4, _5)
	ile = wyk_krok(lista,v,ile, _0, _1, _2, _3, _4, _5, _6, _7 )
	gen(lista, lista[-1], n-1, _4, _3, _2, _5, _6, _1, _0, _7)
	ile = wyk_krok(lista,v,ile, _0, _1, _2, _3, _4, _5, _6, _7 )
	gen(lista, lista[-1], n-1, _4, _3, _2, _5, _6, _1, _0, _7)
	ile = wyk_krok(lista,v,ile, _0, _1, _2, _3, _4, _5, _6, _7 )
	gen(lista, lista[-1], n-1, _6, _5, _2, _1, _0, _3, _4, _7)

''' Only this function is imported '''
def hilbert3D(lista, n):
	gen(lista, lista[-1],n, 0, 1, 2, 3, 4, 5, 6, 7 )
