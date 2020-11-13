from unittest import TestCase
from unittest import main
from two_d_point import TwoDPoint


class TestTwoDPoint(TestCase):

    def test_from_coordinates(self):
        coordinates = [2, 1, 0, 1, 0, 0, 2, 0]
        coordinates2 = [-3, -2, -5, -2, -5, -3, -3, -3]
        coordinates3 = [1, 1, 0, 1, 0, 0, 1, 0]
        self.assertEqual(''.join(map(str, TwoDPoint.from_coordinates(coordinates))), '(2, 1)(0, 1)(0, 0)(2, 0)')
        self.assertEqual(''.join(map(str, TwoDPoint.from_coordinates(coordinates2))), '(-3, -2)(-5, -2)(-5, -3)(-3, -3)')
        self.assertEqual(''.join(map(str, TwoDPoint.from_coordinates(coordinates3))), '(1, 1)(0, 1)(0, 0)(1, 0)')
        self.assertEqual(''.join(map(str, TwoDPoint.from_coordinates([]))), '')
        with self.assertRaises(Exception) as error:
            TwoDPoint.from_coordinates([0])
        # self.assertFalse('[0] is invalid' in str(error.exception))
        with self.assertRaises(Exception) as error:
            TwoDPoint.from_coordinates([-0.0, 1.0, 2.0])
        # self.assertFalse('[-0.0, 1.0, 2.0] is invalid' in str(error.exception))

        self.assertEqual(str(TwoDPoint(0, 1)), '(0, 1)')
        self.assertTrue(TwoDPoint(0, 1) == TwoDPoint(0, 1))
        self.assertTrue(TwoDPoint(0, 1).__ne__(TwoDPoint(1, 2)))

        self.assertEqual(str(TwoDPoint(0, 1) + TwoDPoint(1, 2)), '(1, 3)')
        self.assertEqual(str(TwoDPoint(0, 1) - TwoDPoint(1, 2)), '(-1, -1)')


if __name__ == '__main__':
    main()
