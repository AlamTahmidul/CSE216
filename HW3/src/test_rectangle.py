from unittest import TestCase, main
from rectangle import Rectangle
from two_d_point import TwoDPoint


class TestRectangle(TestCase):
    def test_center(self):
        r1 = Rectangle(2, 1, 0, 1, 0, 0, 2, 0)
        self.assertEqual(str(r1.center()), str(TwoDPoint(1.0, 0.5)))
        with self.assertRaises(TypeError) as error:
            r2 = Rectangle(2, 4, -2, 4, -5, 0, 5, 0)  # Trapezoid
        r3 = Rectangle(1, 1, 0, 1, 0, 0, 1, 0)  # Square
        self.assertEqual(str(r3.center()), str(TwoDPoint(0.5, 0.5)))

    def test_area(self):
        r1 = Rectangle(2, 1.0, 0.0, 1.0, 0.0, 0.0, 2.0, 0.0)
        self.assertEqual(r1.area(), 2.0)
        r3 = Rectangle(1, 1.0, 0, 1, 0, 0, 1, 0)  # Square
        self.assertEqual(r3.area(), 1.0)


if __name__ == '__main__':
    main()
