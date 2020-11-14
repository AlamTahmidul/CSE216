from unittest import TestCase, main
from quadrilateral import Quadrilateral


class TestQuadrilateral(TestCase):

    def test_side_lengths(self):
        # coordinates = [2, 1, 0, 1, 0, 0, 2, 0]
        # coordinates2 = [-3, -2, -5, -2, -5, -3, -3, -3]
        # coordinates3 = [1, 1, 0, 1, 0, 0, 1, 0]
        q1 = Quadrilateral(2, 1, 0, 1, 0, 0, 2, 0)
        q2 = Quadrilateral(2, 4, -2, 4, -5, 0, 5, 0)  # Trapezoid
        q3 = Quadrilateral(1, 1, 0, 1, 0, 0, 1, 0)  # Square
        with self.assertRaises(TypeError) as error:
            q4 = Quadrilateral(1, 1, 0, 1, 0, 0, 1, 0, 5, 6) # Too many arguments

        self.assertEqual(q1.side_lengths(), (2, 1, 2, 1))
        self.assertNotEqual(q2.side_lengths(), (4, 5, 10, 5))
        self.assertEqual(q3.side_lengths(), (1, 1, 1, 1))

    def test_smallest_x(self):
        q1 = Quadrilateral(2, 1, 0, 1, 0, 0, 2, 0)
        q2 = Quadrilateral(2, 4, -2, 4, -5, 0, 5, 0)  # Trapezoid
        q3 = Quadrilateral(1, 1, 0, 1, 0, 0, 1, 0)
        self.assertEqual(q1.smallest_x(), 0)
        self.assertEqual(q2.smallest_x(), -5)
        self.assertEqual(q3.smallest_x(), 0)


if __name__ == '__main__':
    main()
