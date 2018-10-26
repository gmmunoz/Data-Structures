
public class RecursiveBinary {
	public static boolean binarySearchRecursive(int[] array, int x, int left, int right) {
		if (left > right) {
			return false;
		}

		int mid = (left + right) / 2;
		if (array[mid] == x) {
			return true;
		} else if (x < array[mid]) {
			return binarySearchRecursive(array, x, left, mid - 1);
		} else if (x > array[mid]) {
			return binarySearchRecursive(array, x, mid + 1, right);
		}
		return false;

	}
}
