
public class GCD {
	public double GCD(double number1, double number2) {
		if (number2 == 0) {
			return number1;
		} else {
			return GCD(number2, number1 % number2);
		}
	}

	public static void main(String[] args) {
		System.out.println(" " + GCD(27, 9));
	}
}
