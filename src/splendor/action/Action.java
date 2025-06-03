package splendor.action;

import java.util.Scanner;


public interface Action<T> {
	public static final Scanner sc = new Scanner(System.in);
	T run();
}
