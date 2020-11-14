from unittest import TestCase
# from unittest import main
from square import Square


class TestSquare(TestCase):
    def test_snap(self):
        with self.assertRaises(TypeError) as error:
            s1 = Square(2, 1, 0, 1, 0, 0, 2, 0)
        s2 = Square(1, 1, 0, 1, 0, 0, 1, 0)  # Square
        # print(str(s2))
        self.assertEqual(str(s2.snap()), str(s2))
        s3 = Square(1.6, 3.2, -1.6, 3.2, -1.6, 0, 1.6, 0)
        self.assertEqual(str(s3.snap()), str(s3))
        with self.assertRaises(TypeError) as error:
            Square(2, 4, -2, 4, -5, 0, 5, 0)  # Trapezoid


# if __name__ == '__main__':
#     main()
