from quadrilateral import Quadrilateral
from two_d_point import TwoDPoint


class Rectangle(Quadrilateral):

    def __init__(self, *floats):
        super().__init__(*floats)
        if not self.__is_member():
            raise TypeError("A rectangle cannot be formed by the given coordinates.")

    def __eq__(self, other):
        return self.__class__.__name__ == other.__class__.__name__ and super().__eq__(other)

    def __str__(self):
        return super().__str__() + "Center: {}\nArea: {}\n".format(str(self.center()), str(self.area()))

    def __is_member(self):
        """Returns True if the given coordinates form a valid rectangle, and False otherwise."""
        v = self.vertices
        x = v[0].x == v[3].x and v[1].x == v[2].x
        y = v[0].y == v[1].y and v[3].y == v[2].y
        return x and y  # Done

    def center(self):
        """Returns the center of this rectangle, calculated to be the point of intersection of its diagonals."""
        x = (self.vertices[0].x + self.vertices[2].x) / 2
        y = (self.vertices[0].y + self.vertices[2].y) / 2
        return TwoDPoint(x, y)  # Done

    def area(self):
        """Returns the area of this rectangle. The implementation invokes the side_lengths() method from the superclass,
        and computes the product of this rectangle's length and width."""
        lengths = self.side_lengths()
        return lengths[0] * lengths[1]  # Done
