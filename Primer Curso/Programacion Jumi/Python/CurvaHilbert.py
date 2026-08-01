from turtle import left,forward,speed,exitonclick

size = 20

def hilbert(level, angle):
    """Generates Hilbert space-filling curve via recursive turns and advances"""
    if level == 0:
        return
    left(angle)
    hilbert(level - 1, -angle)
    forward(size)
    left(-angle)
    hilbert(level - 1, angle)
    forward(size)
    hilbert(level - 1, angle)
    left(-angle)
    forward(size)
    hilbert(level - 1, -angle)
    left(angle)

speed(0)
hilbert(3, 90)
exitonclick()