from unittest import TestCase, main
from quadrilateral import Quadrilateral


class TestQuadrilateral(TestCase):

    def test_side_lengths(self):
        # coordinates = [2, 1, 0, 1, 0, 0, 2, 0]
        # coordinates2 = [-3, -2, -5, -2, -5, -3, -3, -3]
        # coordinates3 = [1, 1, 0, 1, 0, 0, 1, 0]
        q1 = Quadrilateral(2, 1, 0, 1, 0, 0, 2, 0)
        q2 = Quadrilateral(-3, -2, -5, -2, -5, -3, -3, -3)
        q3 = Quadrilateral(1, 1, 0, 1, 0, 0, 1, 0)

        self.assertEqual(q1.side_lengths(), (2, 1, 2, 1))
        self.assertEqual(q3.side_lengths(), (1, 1, 1, 1))
        # TODO

    def test_smallest_x(self):
        pass # TODO


if __name__ == '__main__':
    main()
