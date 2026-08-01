''' Postscript '''
def postscript(lista,S):
	CONST = S/2 # centers
	print "%!PS-Adobe-2.0 EPSF-2.0"
	print "%%BoundingBox: 0 0 {0} {1}".format(S,S)
	print "newpath"
	
	print "{0} {1} moveto".format(lista[0][0]+CONST, lista[0][1]+CONST)
	for i in lista:
		print "{0} {1} lineto".format( i[0]+CONST, i[1]+CONST)

	print ".4 setlinewidth"
	print "stroke\nshowpage\n%%Trailer\n%EOF"
