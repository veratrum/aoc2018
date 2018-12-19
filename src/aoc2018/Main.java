package aoc2018;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
	
	private static List<Day> days;
	
	public static void main(String[] args) {
		days = new ArrayList<Day>();
		
		int startDay = 1;
		int endDay = 26;
		for (int i = 1; i <= 26; i++) {
			try {
				Day day = (Day) Class.forName("aoc2018.days.Day" + i).newInstance();
				days.add(day);
			} catch (ClassNotFoundException e) {
				startDay = i - 1;
				endDay = i - 1;
				break;
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		startDay = 1;
		//endDay = 26;

		// some solutions are very slow
		boolean skip = true;
		List<Integer> skip1 = Arrays.asList(3, 4, 5, 14, 17, 19);
		List<Integer> skip2 = Arrays.asList(5, 9, 11, 14, 15, 17, 19);
		
		for (int i = startDay; i <= endDay; i++) {
			System.out.println("=== Day " + i + " ===");
			
			try {
				String content = new String(Files.readAllBytes(Paths.get("input/input" + i + ".txt")));

				System.out.print("Part one: ");
				if (skip && skip1.contains(i)) {
					System.out.println("(skipped)");
				} else {
					days.get(i - 1).part1(content);
				}

				System.out.print("Part two: ");
				if (skip && skip2.contains(i)) {
					System.out.println("(skipped)");
				} else {
					days.get(i - 1).part2(content);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
