from typing import List


class TwoDPoint:

    def __init__(self, x, y) -> None:
        self.__x = x
        self.__y = y

    @property
    def x(self):
        return self.__x

    @property
    def y(self):
        return self.__y

    def __eq__(self, other: object) -> bool:
        return self.__x == other.x and self.__y == other.y  # Done

    def __ne__(self, other: object) -> bool:
        return not (self == other)

    def __str__(self) -> str:
        # return '(%g, %g)' % (self.__x, self.__y)
        return "({}, {})".format(str(self.__x), str(self.__y))

    # Done: add magic methods such that two TwoDPoint objects can be added and subtracted coordinate-wise just by using
    #  syntax of the form p + q or p - q
    def __add__(self, other):
        x = self.__x + other.x
        y = self.__y + other.y
        return TwoDPoint(x, y)

    def __sub__(self, other):
        x = self.__x - other.x
        y = self.__y - other.y
        return TwoDPoint(x, y)

    @staticmethod
    def from_coordinates(coordinates: List[float]):
        if len(coordinates) % 2 != 0:
            raise Exception("Odd number of floats given to build a list of 2-d points")
        points = []
        it = iter(coordinates)
        for x in it:
            points.append(TwoDPoint(x, next(it)))
        return points

# point = TwoDPoint(1.0, 2)
# point2 = TwoDPoint(1.1, 2.0)
# point3 = point - point2
# print(point3)
# coordinates1 = [2, 1, 0, 1, 0, 0, 2, 0]
# print(''.join(map(str, TwoDPoint.from_coordinates(coordinates1))))
