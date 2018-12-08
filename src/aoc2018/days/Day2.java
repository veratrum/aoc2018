package aoc2018.days;

import java.util.Arrays;

import aoc2018.Day;

public class Day2 extends Day {

	@Override
	public void part1(String content) {
		String[] rows = content.split(System.getProperty("line.separator"));
		
		int twoTotal = 0;
		int threeTotal = 0;
		
		for (String row: rows) {
			char[] characters = row.toCharArray();
			Arrays.sort(characters);
			
			boolean hasTwo = false;
			boolean hasThree = false;
			
			char previous = '3';
			int frequency = 1;
			for (char character: characters) {
				if (previous == character) {
					frequency++;
				} else {
					if (frequency == 2) {
						hasTwo = true;
					} else if (frequency == 3) {
						hasThree = true;
					}
					
					frequency = 1;
				}
				
				previous = character;
			}
			
			if (frequency == 2) {
				hasTwo = true;
			} else if (frequency == 3) {
				hasThree = true;
			}
			
			if (hasTwo) {
				twoTotal++;
			}
			
			if (hasThree) {
				threeTotal++;
			}
		}
		
		System.out.println(twoTotal * threeTotal);
	}

	@Override
	public void part2(String content) {
		String[] rows = content.split(System.getProperty("line.separator"));
		
		for (int i = 0; i < rows.length; i++) {
			for (int j = i + 1; j < rows.length; j++) {
				if (oneDifference(rows[i], rows[j])) {
					String output = "";
					for (int k = 0; k < rows[i].length(); k++) {
						if (rows[i].charAt(k) == rows[j].charAt(k)) {
							output += rows[i].charAt(k);
						}
					}
					System.out.println(output);
				}
			}
		}
	}
	
	private boolean oneDifference(String a, String b) {
		int differences = 0;
		
		char[] charA = a.toCharArray();
		char[] charB = b.toCharArray();
		
		for (int i = 0; i < charA.length; i++) {
			if (charA[i] != charB[i]) {
				differences++;
			}
			
			if (differences > 1) {
				return false;
			}
		}
		
		return true;
	}

}
