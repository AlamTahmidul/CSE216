from re import findall
from rectangle import Rectangle
from quadrilateral import Quadrilateral


class Square(Rectangle):
    def __init__(self, *floats):
        super().__init__(*floats)
        if not self.__is_member():
            raise TypeError("A square cannot be formed by the given coordinates.")

    def __eq__(self, other):
        return super().__eq__(other)

    def __str__(self):
        return super().__str__()

    def __is_member(self):
        lengths = self.side_lengths()
        return lengths[0] == lengths[1] == lengths[2] == lengths[3]

    def snap(self):
        """Snaps the sides of the square such that each corner (x,y) is modified to be a corner (x',y') where x' is the
        integer value closest to x and y' is the integer value closest to y. This, of course, may change the shape to a
        general quadrilateral, hence the return type. The only exception is when the square is positioned in a way where
        this approximation will lead it to vanish into a single point. In that case, a call to snap() will not modify
        this square in any way."""
        # Done: Check rounding on .5
        flag = True
        index = 0
        new = []
        for i in self.vertices:
            # print(i)
            if (i.x < 0 and len(findall("\d.5[0]*[1-9]+", str(i.x))) == 0) or \
                    (i.y < 0 and len(findall("\d.5[0]*[1-9]+", str(i.y))) == 0):
                if i.x < 0:
                    new.append(int(i.x))
                if i.y < 0:
                    new.append(int(i.y))
            else:
                new.append(round(i.x))
                new.append(round(i.y))
            if index < len(new) - 1:
                if i.x != new[index] or i.y != new[index + 1]:
                    # print("Original: {}, {}".format(str(i.x), str(i.y)))
                    # print("New: {}, {}".format(str(new[index]), str(new[index + 1])))
                    # print("At index = {}\n".format(str(index)))
                    flag = False
            index += 1
            # print("{}, {}".format(i.x, i.y))
        # print("New List: {}".format(new))
        if flag or len(new) != 4:
            return self
        new_quad = Quadrilateral(new)
        # print(str(new_quad))
        v = {(new_quad.vertices[0].x, new_quad.vertices[0].y)}
        for i in new_quad.vertices:
            v.add((i.x, i.y))
        if len(new_quad.vertices) == len(v):
            return new_quad
        else:
            return self  # Done
