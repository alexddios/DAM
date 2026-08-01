def bubble_sort(arr):
    n = len(arr)
    swapped = True

    # Repetimos mientras haya habido algún intercambio
    while swapped:
        swapped = False

        # Recorremos el array hasta n - 1 (comparando i e i+1)
        for i in range(n - 1):
            if arr[i] > arr[i + 1]:
                # Intercambiamos si están en el orden incorrecto
                arr[i], arr[i + 1] = arr[i + 1], arr[i]
                swapped = True

        # El último elemento ya está en su posición correcta
        n -= 1


if __name__ == "__main__":

    unsorted_array = [5, 3, 8, 4, 2]

    bubble_sort(unsorted_array)
    print(unsorted_array)  # [2, 3, 4, 5, 8]
