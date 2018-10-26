package notes.recursion;

public class rec {
	public static int factorial(int n) {

		// base case
		if (n == 0) {
			return 1;
		}

		return n * factorial(n - 1);
	}

	public static int fib(int n) {

		// base cases
		if (n == 0)
			return 1;
		else if (n == 1)
			return 1;

		return fib(n - 1) + fib(n - 2);
	}

	public static final void main(String[] args) {
		int x = Integer.parseInt(args[0]);
		System.out.println(fib(x));
	}
}

// how to make this linearly? stacking!
// look at gradescope (midterm 1) & comp110 videos
