from turtle import *
from colorsys import *
from random import randint, random, choice


def branchPenSize(level):
    return max(level*4, 1)


def traceBranch(level, size, angle):
    tono = 0.028
    saturacion = max((1-level/45), 0.01)
    brillo = max((0.3-level/45), 0.03)

    pencolor(hsv_to_rgb(tono, saturacion, brillo))
    pendown()

    left(angle/2)
    pensize(branchPenSize(level+0.5))
    forward(size/2)

    left(angle/2)
    pensize(branchPenSize(level))
    forward(size/2)


def traceFruit():
    color = choice(["#d03108","#f78719","#f09a73","#e1530c","#bc6b3c"])
    radius = randint(10, 13)
    pensize(1)
    pencolor("#d03108")
    fillcolor(color)

    begin_fill()
    circle(radius)
    end_fill()


def traceLeaf():
    color = choice(["#5d754a","#34521d", "#1c3408", "#558749", "#335f09", "#234106"])
    radius = randint(8, 12)
    angle = randint(0, 200)
    pensize(1)
    pencolor("#1c3408")
    fillcolor(color)

    right(angle)

    begin_fill()
    circle(radius, -180)
    left(90)
    forward(radius*2)
    left(90)
    end_fill()

    right(-angle)


def returnToBranchBase(size, angle):
    penup()
    forward(-size/2)
    right(angle/2)
    forward(-size/2)
    right(angle/2)


def branchSize(level, size):
    return size*0.8+randint(int(-size*1/level), int(size*1/level))


def treeAngle(level, angle):
    return randint(angle-3, angle+8)


def tree(level, size, angle):
    if (level >3) and (level < 6):
        if (random() < 0.05):
            return  # ========================>
        
    if (level<3):
        if (random() < 0.1):
            traceFruit()
            return # =========================>

    if (level >=0) and (level<=3):
        traceLeaf()

    if (level <= 0):
        return  # ============================>

    traceBranch(level, size, angle)

    tree(level-1, branchSize(level, size), treeAngle(level, angle))

    tree(level-1, branchSize(level, size), -treeAngle(level, angle))

    returnToBranchBase(size, angle)


speed(0)
penup()
goto(0, -325)
pendown()
hideturtle()
left(65)


bgcolor("black")
tree(12, 90, 20)
print("Ended")

exitonclick()
