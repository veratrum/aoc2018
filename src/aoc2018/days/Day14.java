package aoc2018.days;

import java.util.ArrayList;
import java.util.List;

import aoc2018.Day;

public class Day14 extends Day {

	@Override
	public void part1(String content) {
		int elf1 = 0;
		int elf2 = 1;
		
		int iterations = Integer.parseInt(content);
		
		String recipes = "37";
		
		boolean done = false;
		while (!done) {
			int digit1 = Character.getNumericValue(recipes.charAt(elf1));
			int digit2 = Character.getNumericValue(recipes.charAt(elf2));
			
			int sum = digit1 + digit2;
			if (sum >= 10) {
				recipes += "1";
				recipes += (sum - 10);
			} else {
				recipes += sum;
			}
			
			elf1 += digit1 + 1;
			elf2 += digit2 + 1;
			elf1 %= recipes.length();
			elf2 %= recipes.length();
			
			if (recipes.length() >= iterations + 10) {
				done = true;
				System.out.println(recipes.substring(iterations, iterations + 10));
			}
		}
	}

	@Override
	public void part2(String content) {
		int elf1 = 0;
		int elf2 = 1;
		
		List<Short> input = new ArrayList<Short>();
		for (int i = 0; i < content.length(); i++) {
			input.add((short) Character.getNumericValue(content.charAt(i)));
		}
		
		List<Short> recipes = new ArrayList<Short>();
		recipes.add((short) 3);
		recipes.add((short) 7);
		
		boolean done = false;
		while (!done) {
			short digit1 = recipes.get(elf1);
			short digit2 = recipes.get(elf2);
			
			short sum = (short) (digit1 + digit2);
			if (sum >= 10) {
				recipes.add((short) 1);
				sum -= 10;
			}
			
			if (recipes.size() >= 6) {
				boolean valid = true;
				for (int i = 0; valid && i < input.size(); i++) {
					if (recipes.get(recipes.size() - 1 - i) != input.get(input.size() - 1 - i)) {
						valid = false;
					}
				}
				
				if (valid) {
					done = true;
					System.out.println(recipes.size() - input.size());
				}
			}
			
			recipes.add(sum);
			
			elf1 += digit1 + 1;
			elf2 += digit2 + 1;
			elf1 %= recipes.size();
			elf2 %= recipes.size();
			
			if (recipes.size() >= 6) {
				boolean valid = true;
				for (int i = 0; valid && i < input.size(); i++) {
					if (recipes.get(recipes.size() - 1 - i) != input.get(input.size() - 1 - i)) {
						valid = false;
					}
				}
				
				if (valid) {
					done = true;
					System.out.println(recipes.size() - input.size());
				}
			}
		}
	}

}
