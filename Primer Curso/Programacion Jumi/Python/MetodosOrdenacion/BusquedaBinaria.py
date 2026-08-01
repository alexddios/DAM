def binary_search(arr, x):
    inicio=0
    fin = len(arr)-1

    while inicio<=fin:
        centro=inicio+(fin-inicio)//2
        valor_central=arr[centro]

        if valor_central == x:
            return centro
        elif x<valor_central:
            fin =centro-1
        else:
            inicio=centro+1
    return -1
if __name__=="__main__":
    array=[4,5,9,12,14,15,20,22]
    x=15
    pos = binary_search(array,x)

    if pos !=-1:
        print (f"Valor {x} encontrado en la posición {pos}")
    else:
        print (f"Valor {x} no encontrado en el array")