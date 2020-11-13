from two_d_point import TwoDPoint


class Quadrilateral:
    def __init__(self, *floats):
        points = TwoDPoint.from_coordinates(list(floats))
        self.__vertices = tuple(points[0:4])
        if not self.__is_member():
            raise TypeError("A quadrilateral cannot be formed from given coordinates")

    def __eq__(self, other):
        v1 = {(self.__vertices[0].x, self.__vertices[0].y)}
        v2 = {other.vertices[0].x, other.vertices[0].y}
        for i in self.__vertices:
            v1.add((i.x, i.y))
        for i in other.vertices:
            v2.add((i.x, i.y))
        return v1 == v2

    def __str__(self):
        s1 = "{} Information:\n".format(self.__class__.__name__)
        s2 = "Vertices: "
        for i in self.__vertices:
            s2 += "(" + str(i.x) + ", " + str(i.y) + "), "
        s2 = s2[0:len(s2)-2] + "\n"
        return s1 + s2

    def __is_member(self):
        """Returns True if the given coordinates form a valid quadrilateral, and False otherwise."""
        v = {(self.__vertices[0].x, self.__vertices[0].y)}
        for i in self.__vertices:
            v.add((i.x, i.y))
        # print(v)
        return len(self.__vertices) == len(v)

    @property
    def vertices(self):
        return self.__vertices

    def side_lengths(self):
        """Returns a tuple of four floats, each denoting the length of a side of this quadrilateral. The value must be
        ordered clockwise, starting from the top left corner."""
        vertices = self.__vertices
        zero3 = abs(vertices[0].y - vertices[3].y)
        three2 = abs(vertices[3].x - vertices[2].x)
        two1 = abs(vertices[2].y - vertices[1].y)
        one0 = abs(vertices[1].x - vertices[0].x)
        return zero3, three2, two1, one0  # Done

    def smallest_x(self):
        """Returns the x-coordinate of the vertex with the smallest x-value of the four vertices of this
        quadrilateral."""
        return min(self.__vertices[0].x, self.__vertices[1].x, self.__vertices[2].x, self.__vertices[3].x)  # Done
