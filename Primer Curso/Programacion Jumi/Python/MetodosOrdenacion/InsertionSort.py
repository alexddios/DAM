def insertion_sort(arr):
    for j in range(1, len(arr)):
        key = arr[j]
        i = j - 1
        # El desplazamiento ocurre DENTRO del bucle principal
        while i >= 0 and arr[i] > key:
            arr[i + 1] = arr[i]
            i -= 1
        arr[i + 1] = key
if __name__ == "__main__":
    unsorted_array = [3, -7, 10, -4, 0, 6, -1, 8, -9, 2]
    print("Desordenado : ", unsorted_array)
    insertion_sort(unsorted_array)
    print("Ordenado : ", unsorted_array) # [-9, -7, -4, -2, -1, 2, 3, 6, 8, 10]