from unittest import TestCase, main
from square import Square


class TestSquare(TestCase):
    def test_snap(self):
        with self.assertRaises(TypeError) as error:
            s1 = Square(2, 1, 0, 1, 0, 0, 2, 0)
        s2 = Square(1, 1, 0, 1, 0, 0, 1, 0)  # Square



if __name__ == '__main__':
    main()
