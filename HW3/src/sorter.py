from quadrilateral import Quadrilateral
from rectangle import Rectangle
from square import Square
from two_d_point import TwoDPoint


class ShapeSorter(Quadrilateral):
    @staticmethod
    def sort(*quads):
        result = [str(x) for x in sorted(quads, key=Quadrilateral.smallest_x)]
        return result


# Done: Test Code
# coordinates = 2, 1, 0, 1, 0, 0, 2, 0
# coordinates2 = -3, -2, -5, -2, -5, -3, -3, -3
# coordinates3 = 1, 1, 0, 1, 0, 0, 1, 0
q1 = Quadrilateral(2, 1, 0, 1, 0, 0, 2, 0)
print(q1)
r1 = Rectangle(2, 1, 0, 1, 0, 0, 2, 0)
# r1 = Rectangle(-3, -2, -5, -2, -5, -3, -3, -3)
print(r1)
s1 = Square(1, 1, 0, 1, 0, 0, 1, 0)
print(s1)
shapes = q1, r1, s1
print(ShapeSorter.sort(q1, r1, s1))
