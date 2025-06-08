package splendor.action;

import java.io.ByteArrayInputStream;
import java.io.SequenceInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public interface Action<T> {
	
	public static final Scanner sc = new Scanner(new SequenceInputStream(
			new ByteArrayInputStream(
					"2 ethan 20 anais 2 2 2".getBytes(StandardCharsets.UTF_8)), 
		            System.in));
	T run();
}
