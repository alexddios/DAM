def selection_sort(arr):
    n = len(arr)
    for i in range(n - 1):
        min_index = i
        for j in range(i + 1, n):
            if arr[j] < arr[min_index]:
                min_index = j
        # Intercambio al final de cada pasada externa
        arr[i], arr[min_index] = arr[min_index], arr[i]
 # Ejemplo de uso
if __name__ == "__main__" :
     unsorted_array = [3, -7, 10, -4, 0, 6, -1, 8, -9, 2]
     print("Desordenado : ", unsorted_array)
     selection_sort(unsorted_array)
     print("Ordenado : ",unsorted_array)  # [-9, -7, -4, -1, 0, 2, 3, 6, 8, 10]