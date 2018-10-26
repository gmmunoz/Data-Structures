import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SymbolBalance2 {
	static MyStack<Character> stackOpen = new MyStack<Character>();
	private static char[] OPENING = { '{', '(', '[' };
	private static char[] CLOSING = { '}', ')', ']' };

	static String prob = "";

	static boolean openC = false;
	static boolean closeC = false;

	static boolean openQ = false;
	static boolean closeQ = false;

	// symbol iterator
	public static void goThrough(ArrayList<Character> in) {
		char x;
		for (int i = 0; i < in.size(); i++) {
			x = in.get(i);
			// identifies opening comment
			if (x == '/' && !openQ) {
				i++;
				x = in.get(i);
				if (x == '*') {
					openC = true;
				}
			}
			// identifies (
			else if (x == '*' && !openQ) {
				i++;
				x = in.get(i);
				if (x == '/') {
					// makes sure its actually closing a comment and not
					// unbalanced symbol
					if (openC) {

						openC = false;
					} else {
						prob += "mismatch: */";
					}
				}
			}
			// handles quotes (String literals)
			if (x == '"' && !openC) {
				// opening a quote
				if (!openQ) {
					openQ = true;

				} else { // closing a quote
					openQ = false;
				}
			}
			// if not a special condition, calls method to check for symbols
			if (openC && !closeC && !openQ) {
				openCheck(x);
				closeCheck(x);
			}

		}

	}

	// checks for opening symbols, pushes them into stack
	public static void openCheck(char in) {

		for (int i = 0; i < OPENING.length; i++) {

			if (in == OPENING[i]) {
				// push into stack
				stackOpen.push(in);

			}
		}
	}

	// checks for closing symbols, compares to stack
	public static void closeCheck(char in) {
		char popped;
		int missCount = 0;
		for (int i = 0; i < CLOSING.length; i++) {
			// encounters closing char, popped from stack isn't the
			// corresponding
			// opening symbol (missing opening symbol)
			if (in == CLOSING[i]) {
				// unbalanced closing symbol
				if (!stackOpen.isEmpty()) {
					popped = stackOpen.pop();
					if (popped != CLOSING[i]) {
						missCount++;
					}
					if (missCount == 3) {
						prob += "Unbalanced Symbol " + popped + " is present but " + OPENING[i] + " is missing";
					}
				} else {
					prob += in + " without corresponding opening symbol";
				}
			}
		}
	}

	// check if there is anything left in stack
	public static void emptyStack(ArrayList<Character> in) {

		if (stackOpen.isEmpty() == false) {

			prob += "Symbol: " + stackOpen.pop() + " does not have closing tag ";

			if (!stackOpen.isEmpty()) {
				emptyStack(in);
			}
		}

		// checks for missing closing quote/comment
		if (openQ) {
			prob += " Missing closing quotation mark";
		}
		if (openC && !closeC) {
			prob += " Missing closing comment marker ";
		}

	}

	public static void main(String[] args) {
		String fileName = args[0];
		ArrayList<Character> exp = new ArrayList<>();

		// looked at
		// https://www.caveofprogramming.com/java/java-file-reading-and-writing-files-in-java.html
		// to get idea of how to read inputed file exactly
		try {
			char g;
			int m;

			FileReader freader = new FileReader(fileName);
			BufferedReader breader = new BufferedReader(freader);
			// reads file character by character, adds (non empty) to arraylist
			// of chars
			while ((m = breader.read()) != -1) {
				g = (char) m;
				if (g != ' ') {
					exp.add(g);
				}
			}
			breader.close();
		} catch (FileNotFoundException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (IOException ex) {
			System.out.println("Error reading file '" + fileName + "'");
		}

		goThrough(exp);
		emptyStack(exp);
		System.out.println(prob);
		if (prob.isEmpty()) {
			System.out.println("All symbols are balance");
		}

	}

}
