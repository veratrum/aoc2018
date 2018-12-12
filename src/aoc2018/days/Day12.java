package aoc2018.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import aoc2018.Day;

public class Day12 extends Day {

	@Override
	public void part1(String content) {
		String[] lines = content.split(System.getProperty("line.separator"));
		
		String[] fragments = lines[0].split(" ");
		String startState = fragments[2];
		
		String[] sorted = new String[lines.length - 2];
		for (int i = 2; i < lines.length; i++) {
			sorted[i - 2] = lines[i];
		}
		Arrays.sort(sorted);
		sorted = reverse(sorted);
		
		List<Boolean> rules = new ArrayList<Boolean>();
		for (int i = 0; i < sorted.length; i++) {
			fragments = sorted[i].split(" => ");
			if (fragments[1].equals("#")) {
				rules.add(true);
			} else {
				rules.add(false);
			}
		}
		
		List<Boolean> pots = new ArrayList<Boolean>();
		
		for (int i = -100; i < startState.length() + 100; i++) {
			if (i < 0 || i >= startState.length()) {
				pots.add(false);
				continue;
			}
			
			char pot = startState.charAt(i);
			if (pot == '#') {
				pots.add(true);
			} else {
				pots.add(false);
			}
		}
		
		int iterations = 20;
		for (int i = 0; i < iterations; i++) {
			List<Boolean> nextPots = new ArrayList<Boolean>();
			for (int j = 0; j < startState.length() + 200; j++) {
				if (j < 2 || j >= startState.length() + 198) {
					nextPots.add(false);
					continue;
				}
				
				int code = 0;
				for (int k = 0; k <= 4; k++) {
					if (pots.get(j + k - 2)) {
						code += power(2, (4 - k));
					}
				}
				
				nextPots.add(rules.get(code));
			}
			
			pots = nextPots;
		}
		
		int sum = 0;
		for (int i = 0; i < pots.size(); i++) {
			if (pots.get(i)) {
				sum += (i - 100);
			}
		}
		System.out.println(sum);
	}

	@Override
	public void part2(String content) {
		String[] lines = content.split(System.getProperty("line.separator"));
		
		String[] fragments = lines[0].split(" ");
		String startState = fragments[2];
		
		String[] sorted = new String[lines.length - 2];
		for (int i = 2; i < lines.length; i++) {
			sorted[i - 2] = lines[i];
		}
		Arrays.sort(sorted);
		sorted = reverse(sorted);
		
		List<Boolean> rules = new ArrayList<Boolean>();
		for (int i = 0; i < sorted.length; i++) {
			fragments = sorted[i].split(" => ");
			if (fragments[1].equals("#")) {
				rules.add(true);
			} else {
				rules.add(false);
			}
		}
		
		List<Boolean> pots = new ArrayList<Boolean>();
		
		for (int i = -1000; i < startState.length() + 1000; i++) {
			if (i < 0 || i >= startState.length()) {
				pots.add(false);
				continue;
			}
			
			char pot = startState.charAt(i);
			if (pot == '#') {
				pots.add(true);
			} else {
				pots.add(false);
			}
		}
		
		int iterations = 200;
		List<Integer> sums = new ArrayList<Integer>();
		for (int i = 0; i < iterations; i++) {
			List<Boolean> nextPots = new ArrayList<Boolean>();
			for (int j = 0; j < startState.length() + 2000; j++) {
				if (j < 2 || j >= startState.length() + 1998) {
					nextPots.add(false);
					continue;
				}
				
				int code = 0;
				for (int k = 0; k <= 4; k++) {
					if (pots.get(j + k - 2)) {
						code += power(2, (4 - k));
					}
				}
				
				nextPots.add(rules.get(code));
			}
			
			pots = nextPots;
			
			int sum = 0;
			for (int j = 0; j < pots.size(); j++) {
				if (pots.get(j)) {
					sum += (j - 1000);
				}
			}
			sums.add(sum);
		}
		
		int difference = sums.get(sums.size() - 1) - sums.get(sums.size() - 2);
		long finalSum = sums.get(sums.size() - 1) + difference * (50000000000L - sums.size());
		System.out.println(finalSum);
	}
	
	private String[] reverse(String[] array) {
		for(int i = 0; i < array.length / 2; i++) {
			String temp = array[i];
			array[i] = array[array.length - i - 1];
			array[array.length - i - 1] = temp;
		}
		
		return array;
	}
	
	private int power(int base, int exponent) {
		if (exponent == 0) {
			return 1;
		}
		
		return base * power(base, exponent - 1);
	}

}
