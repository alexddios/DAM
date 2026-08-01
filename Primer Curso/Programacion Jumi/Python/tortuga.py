import turtle as t


def k(length, size):
    if length == 1:
        t.forward(size)
        return

    k(length - 1, size / 3)
    t.left(60)
    k(length - 1, size / 3)
    t.left(-120)
    k(length - 1, size / 3)
    t.left(60)
    k(length - 1, size / 3)


def main():
    t.speed(0)
    t.setup(900, 900)

    length = 4
    size = 300  # Más grande que antes
    for _ in range(2):
        for _ in range(3):
            k(length, size)
            t.right(120)
        t.left(180)

    t.exitonclick()


if __name__ == "__main__":
    main()