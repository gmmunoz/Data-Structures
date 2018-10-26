package notes.recursion;

public class BinarySearchGenerics {
	public static <AnyType extends Comparable<AnyType>> int search(AnyType[] arr, AnyType x) {
		// classes can extend things, interfaces can implements
		// when declaring the generic with in <> only use EXTENDS****
		int start;
		int stop;

		start = 0;
		stop = arr.length - 1;

		int mid;

		while (start <= stop) {
			mid = (start + stop) / 2;

			if (arr[mid].compareTo(x) > 0) { // first half
				stop = mid - 1;
			} else if (arr[mid].compareTo(x) < 0) { // second half
				start = mid + 1;
				// arr will be replaced with any type we choose to replace it
				// when you have a generic it NEEDS to have classes (must be in
				// integers) --> only object references (NO primitives)
			} else {
				return mid;
			}
			return -1;
		}
	}

	public static void main(String[] args) {
		Person[] people = { new Person("Blaer", "Paul"), new Person("Blaer", "Rae"), new Person("Doe", "John"),
				new Person("Wayne", "John") };

		System.out.println(search(people, new Person("Blaer", "Rae")));

		String[] strings = { "A", "B", "C", "D", "E" };
		System.out.println(search(strings, "D"));
	}

}

// private method will have recursive method
// user never calls private